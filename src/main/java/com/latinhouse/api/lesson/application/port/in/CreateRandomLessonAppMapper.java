package com.latinhouse.api.lesson.application.port.in;

import com.latinhouse.api.lesson.domain.DiscountType;
import com.latinhouse.api.lesson.domain.Genre;
import com.latinhouse.api.lesson.domain.Region;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CreateRandomLessonAppMapper {

    private static final String[] LEVELS = {"초급", "중급", "상급"};
    private static final int[] START_HOURS = {10, 14, 19, 20};
    private static final BigDecimal[] AMOUNTS = {
            new BigDecimal("30000"), new BigDecimal("50000"),
            new BigDecimal("80000"), new BigDecimal("100000")
    };
    private static final BigDecimal[] DISCOUNT_AMOUNTS = {
            new BigDecimal("5000"), new BigDecimal("10000"), new BigDecimal("15000")
    };

    private CreateRandomLessonAppMapper() {}

    public static CreateLessonAppRequest toCreateLessonAppRequest(String instructorLo, String instructorLa) {
        ThreadLocalRandom rng = ThreadLocalRandom.current();

        Genre genre = rng.nextBoolean() ? Genre.SALSA : Genre.BACHATA;
        String genreLabel = genre == Genre.SALSA ? "살사" : "바차타";
        String title = genreLabel + " " + LEVELS[rng.nextInt(LEVELS.length)] + "반";

        int optionCount = rng.nextInt(1, 4); // 1~3
        List<CreateLessonAppRequest.OptionAppReq> options = new ArrayList<>();
        LocalDate earliestStartDate = null;

        for (int i = 0; i < optionCount; i++) {
            LocalDate startDate = LocalDate.now().plusDays(7 + rng.nextInt(54));
            int startHour = START_HOURS[rng.nextInt(START_HOURS.length)];
            LocalDateTime startDateTime = startDate.atTime(startHour, 0);
            LocalDateTime endDateTime = startDateTime.plusHours(2);
            Region region = rng.nextBoolean() ? Region.GANGNAM : Region.HONGDAE;

            if (earliestStartDate == null || startDate.isBefore(earliestStartDate)) {
                earliestStartDate = startDate;
            }

            options.add(CreateLessonAppRequest.OptionAppReq.builder()
                    .startDateTime(startDateTime)
                    .endDateTime(endDateTime)
                    .region(region)
                    .place(null)
                    .placeUrl(null)
                    .build());
        }

        BigDecimal amount = AMOUNTS[rng.nextInt(AMOUNTS.length)];

        int discountCount = rng.nextInt(3); // 0~2
        List<CreateLessonAppRequest.DiscountAppReq> discounts = new ArrayList<>();
        for (int i = 0; i < discountCount; i++) {
            DiscountType type = rng.nextBoolean() ? DiscountType.EARLYBIRD : DiscountType.SEX;
            String condition;
            if (type == DiscountType.EARLYBIRD) {
                condition = earliestStartDate.minusDays(7).toString();
            } else {
                condition = rng.nextBoolean() ? "M" : "F";
            }
            BigDecimal discountAmount = DISCOUNT_AMOUNTS[rng.nextInt(DISCOUNT_AMOUNTS.length)];

            discounts.add(CreateLessonAppRequest.DiscountAppReq.builder()
                    .type(type)
                    .condition(condition)
                    .amount(discountAmount)
                    .build());
        }

        return CreateLessonAppRequest.builder()
                .title(title)
                .genre(genre)
                .instructorLo(instructorLo)
                .instructorLa(instructorLa)
                .options(options)
                .amount(amount)
                .discounts(discounts)
                .account(null)
                .contacts(null)
                .isActive(true)
                .notices(null)
                .build();
    }

    public static CreateRandomLessonAppResponse toAppResponse(CreateLessonAppResponse response) {
        return CreateRandomLessonAppResponse.builder()
                .id(response.getId())
                .build();
    }
}
