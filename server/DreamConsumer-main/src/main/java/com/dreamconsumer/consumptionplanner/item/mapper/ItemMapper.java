package com.dreamconsumer.consumptionplanner.item.mapper;

import com.dreamconsumer.consumptionplanner.item.domain.Item;
import com.dreamconsumer.consumptionplanner.item.dto.ItemPatchDto;
import com.dreamconsumer.consumptionplanner.item.dto.ItemRequestDto;
import com.dreamconsumer.consumptionplanner.item.dto.ItemResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    Item itemRequestDtoToItem(ItemRequestDto itemRequestDto);

    ItemResponseDto ItemToItemResponseDto(Item item);
    void itemPatchDtoToitem(ItemPatchDto itemPatchDto, @MappingTarget Item item);


}