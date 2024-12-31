package com.latinhouse.backend.checkout.domain;

import com.latinhouse.backend.profile.domain.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long seq;
    private Long lessonNo;
    private String profileId;
}
