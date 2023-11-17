package com.dreamconsumer.consumptionplanner.member_item.mapper;

import com.dreamconsumer.consumptionplanner.item.dto.ItemRequestDto;
import com.dreamconsumer.consumptionplanner.member_item.domain.MemberItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberItemMapper {
    @Mapping(target = "totalMoney", source = "currentMoney")
    MemberItem itemRequestToMemberItem(ItemRequestDto itemRequestDto);
}