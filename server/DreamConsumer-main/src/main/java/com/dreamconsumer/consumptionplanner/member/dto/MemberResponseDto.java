package com.dreamconsumer.consumptionplanner.member.dto;

import com.dreamconsumer.consumptionplanner.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponseDto {
    private Member.Tier tier;
    private long userId;
}
