package com.example.demo.item.dto;

import com.example.demo.user_item.domain.UserItem;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Getter
public class ItemRequestDto {

    @NotNull
    private Long userId;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal price;

    // 태그

    private String itemUrl;

    @NotNull
    private BigDecimal currentMoney;

    @NotNull
    @Pattern(regexp = "^(DAILY|WEEKLY|MONTHLY)$", message = "Invalid cycle type")
    private UserItem.Cycle cycle;

    @NotNull
    private BigDecimal unitAmount;

    @NotNull
    private boolean autoUpdate;

    @NotNull
    private boolean groupPurchase;
}