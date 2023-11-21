package com.dreamconsumer.consumptionplanner.member.mapper;

import com.dreamconsumer.consumptionplanner.member.domain.Member;
import com.dreamconsumer.consumptionplanner.member.dto.MemberMyPageResponseDto;
import com.dreamconsumer.consumptionplanner.member.dto.MemberPostDto;
import com.dreamconsumer.consumptionplanner.member.dto.MemberResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    @Mapping(target="name", source="userName")
    Member MemberPostDtoToMember(MemberPostDto memberPostDto);
    @Mapping(target = "userId", source = "memberId")
    MemberResponseDto MemberToMemberResponseDto(Member member);
    @Mapping(target = "userName", source = "name")
    MemberMyPageResponseDto MemberToMemberMyPageResponseDto(Member member);
}
