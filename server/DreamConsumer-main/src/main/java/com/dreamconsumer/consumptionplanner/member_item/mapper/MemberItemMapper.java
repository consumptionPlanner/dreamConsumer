package com.dreamconsumer.consumptionplanner.member_item.mapper;

import com.dreamconsumer.consumptionplanner.item.dto.ItemRequestDto;
import com.dreamconsumer.consumptionplanner.member_item.domain.MemberItem;
import com.dreamconsumer.consumptionplanner.tag.domain.Tag;
import com.dreamconsumer.consumptionplanner.tag.mapper.TagMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {TagMapper.class})
public interface MemberItemMapper {

    @Mapping(target = "totalMoney", source = "currentMoney")
    @Mapping(target = "tags", ignore = true)
    MemberItem itemRequestToMemberItem(ItemRequestDto itemRequestDto);

//    @AfterMapping
//    default void mapTags(ItemRequestDto itemRequestDto, @MappingTarget MemberItem memberItem, TagMapper tagMapper) {
//        // iterm
//        List<String> stringTags = itemRequestDto.getTags();
//        if ( stringTags != null) {
//            List<Tag> tags = new ArrayList<>();
//            tagMapper.stringsToTags(stringTags, memberItem);
//        }
//    }
}