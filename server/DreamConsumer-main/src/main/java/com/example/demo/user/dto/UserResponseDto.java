package com.example.demo.user.dto;

import com.example.demo.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private long userId;

    private User.Tier tier;
}
