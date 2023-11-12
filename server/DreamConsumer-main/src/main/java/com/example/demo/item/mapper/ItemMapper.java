package com.example.demo.item.mapper;

import com.example.demo.item.domain.Item;
import com.example.demo.item.dto.ItemPatchDto;
import com.example.demo.item.dto.ItemRequestDto;
import com.example.demo.item.dto.ItemResponseDto;
import com.example.demo.user_item.domain.UserItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    Item itemRequestDtoToItem(ItemRequestDto itemRequestDto);

    ItemResponseDto ItemToItemResponseDto(Item item);
    void itemPatchDtoToitem(ItemPatchDto itemPatchDto, @MappingTarget Item item);


}