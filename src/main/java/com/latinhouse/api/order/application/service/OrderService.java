package com.latinhouse.api.order.application.service;

import com.latinhouse.api.global.exception.CustomException;
import com.latinhouse.api.global.exception.ErrorCode;
import com.latinhouse.api.lesson.domain.Lesson;
import com.latinhouse.api.lesson.port.out.ReadLessonPort;
import com.latinhouse.api.order.domain.Order;
import com.latinhouse.api.order.port.in.CreateOrderUseCase;
import com.latinhouse.api.order.port.in.FindOrderUseCase;
import com.latinhouse.api.order.port.in.request.CreateOrderAppRequest;
import com.latinhouse.api.order.port.in.request.FindOrderAppRequest;
import com.latinhouse.api.order.port.in.response.OrderAppResponse;
import com.latinhouse.api.order.port.in.response.PagedOrderAppResponse;
import com.latinhouse.api.order.port.out.CreateOrderPort;
import com.latinhouse.api.order.port.out.ReadOrderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService implements CreateOrderUseCase, FindOrderUseCase {

    private final CreateOrderPort createOrderPort;
    private final ReadOrderPort readOrderPort;
    private final ReadLessonPort readLessonPort;

    @Override
    public OrderAppResponse create(CreateOrderAppRequest appReq) {
        // 변경 2: lessonNo 존재 확인
        Lesson lesson = readLessonPort.findByNo(appReq.getLessonNo())
                .orElseThrow(() -> new CustomException(ErrorCode.LESSON_NOT_FOUND));

        // 변경 2: optionNo 존재 확인
        boolean optionExists = lesson.getOptions() != null && lesson.getOptions().stream()
                .anyMatch(option -> option.getNo().equals(appReq.getOptionNo()));
        if (!optionExists) {
            throw new CustomException(ErrorCode.LESSON_OPTION_NOT_FOUND);
        }

        // 변경 4: createdBy/modifiedBy를 profileId로 설정 (SecurityContext 미사용)
        String profileId = appReq.getProfileId();

        Order order = Order.builder()
                .lessonNo(appReq.getLessonNo())
                .optionNo(appReq.getOptionNo())
                .profileId(profileId)
                .status(appReq.getStatus())
                .createdBy(profileId)
                .modifiedBy(profileId)
                .build();

        return new OrderAppResponse(createOrderPort.create(order));
    }

    @Override
    public OrderAppResponse findByNo(Long no) {
        return readOrderPort.findByNo(no)
                .map(OrderAppResponse::new)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
    }

    @Override
    public PagedOrderAppResponse findAll(int page, int size, FindOrderAppRequest searchReq) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "no"));
        return new PagedOrderAppResponse(
                readOrderPort.findAll(pageRequest, searchReq).map(OrderAppResponse::new));
    }
}
