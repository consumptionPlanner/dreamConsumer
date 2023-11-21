package com.dreamconsumer.consumptionplanner.member.dto;

import com.dreamconsumer.consumptionplanner.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberMyPageResponseDto {
    private long userId;

    private Member.Tier tier;

    private String userName;

    private String email;

    private String job;

    private int age;

    // 등급

    //성공한 item 개수

    // 성공한 items pagination
}
