package com.dreamconsumer.consumptionplanner.member.dto;

import com.dreamconsumer.consumptionplanner.member.domain.Member;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class MemberLoginResponseDto {
    private Member.Tier tier;
    private Long userId;
}
