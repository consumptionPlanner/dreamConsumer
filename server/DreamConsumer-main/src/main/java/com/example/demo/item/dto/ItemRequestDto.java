package com.example.demo.item.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ItemRequestDto {
    private Long userId;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal price;

    @NotNull
    private BigDecimal currentMoney;

//    @NotNull
//    @Pattern(regexp = "^(DAILY|WEEKLY|MONTHLY)$", message = "Invalid cycle type")
//    private Item.Cycle cycle;

    @NotNull
    private BigDecimal unitAmount;
    @NotNull
    private boolean autoUpdate;
    @NotNull
    private boolean groupPurchase;
}