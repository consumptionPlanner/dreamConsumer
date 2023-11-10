package com.example.demo.item.mapper;

import com.example.demo.item.domain.Item;
import com.example.demo.item.dto.ItemPatchDto;
import com.example.demo.item.dto.ItemRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    Item itemRequestDtoToItem(ItemRequestDto itemRequestDto);
    void itemPatchDtoToitem(ItemPatchDto itemPatchDto, @MappingTarget Item item);
}