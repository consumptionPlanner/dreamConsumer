package com.dreamconsumer.consumptionplanner.item.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemResponseDto {

    private Long id;
    private String groupPurchaseUrl="구현 중";
}
