package com.latinhouse.api.order.adapter.in.web;

import com.latinhouse.api.common.config.SecurityConfig;
import com.latinhouse.api.common.exception.GlobalExceptionHandler;
import com.latinhouse.api.common.exception.LessonNotFoundException;
import com.latinhouse.api.common.exception.LessonOptionNotFoundException;
import com.latinhouse.api.common.exception.ProfileNotFoundException;
import com.latinhouse.api.order.application.port.in.CreateOrderAppResponse;
import com.latinhouse.api.order.application.port.in.CreateOrderUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@Import({GlobalExceptionHandler.class, SecurityConfig.class})
class OrderControllerTest {

    private static final String VALID_BODY =
            "{\"lessonNo\":1,\"lessonOptionNo\":3,\"profileId\":\"Ab2Cd3Ef\"}";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateOrderUseCase createOrderUseCase;

    @Test
    void createOrder_validRequest_returns201WithOrderId() throws Exception {
        when(createOrderUseCase.createOrder(any()))
                .thenReturn(new CreateOrderAppResponse("550e8400-e29b-41d4-a716-446655440000"));

        mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId").value("550e8400-e29b-41d4-a716-446655440000"));
    }

    @Test
    void createOrder_nullLessonNo_returns400() throws Exception {
        String body = "{\"lessonNo\":null,\"lessonOptionNo\":3,\"profileId\":\"Ab2Cd3Ef\"}";

        mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createOrder_nullLessonOptionNo_returns400() throws Exception {
        String body = "{\"lessonNo\":1,\"lessonOptionNo\":null,\"profileId\":\"Ab2Cd3Ef\"}";

        mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createOrder_blankProfileId_returns400() throws Exception {
        String body = "{\"lessonNo\":1,\"lessonOptionNo\":3,\"profileId\":\"\"}";

        mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createOrder_lessonNotFound_returns404() throws Exception {
        when(createOrderUseCase.createOrder(any()))
                .thenThrow(new LessonNotFoundException(999L));

        mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isNotFound());
    }

    @Test
    void createOrder_lessonOptionNotFound_returns404() throws Exception {
        when(createOrderUseCase.createOrder(any()))
                .thenThrow(new LessonOptionNotFoundException(999L));

        mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isNotFound());
    }

    @Test
    void createOrder_profileNotFound_returns404() throws Exception {
        when(createOrderUseCase.createOrder(any()))
                .thenThrow(new ProfileNotFoundException("NoExist1"));

        mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isNotFound());
    }
}
