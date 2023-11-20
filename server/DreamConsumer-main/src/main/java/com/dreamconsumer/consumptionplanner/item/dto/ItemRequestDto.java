package com.dreamconsumer.consumptionplanner.item.dto;

import com.dreamconsumer.consumptionplanner.member_item.domain.MemberItem;
import com.dreamconsumer.consumptionplanner.tag.domain.Tag;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

@Getter
public class ItemRequestDto {

    @NotNull
    private Long userId;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal price;

    private List<String> tags;

    private String itemUrl;

    private String imageUrl;

    @NotNull
    private BigDecimal currentMoney;

    @NotNull
    @Pattern(regexp = "^(DAILY|WEEKLY|MONTHLY)$", message = "Invalid Cycle Type")
    private MemberItem.Cycle cycle;

    @NotNull
    private BigDecimal unitAmount;

    @NotNull
    private Boolean autoUpdate;

    @NotNull
    private Boolean groupPurchase;
}