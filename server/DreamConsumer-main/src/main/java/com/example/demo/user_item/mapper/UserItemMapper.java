package com.example.demo.user_item.mapper;

import com.example.demo.item.domain.Item;
import com.example.demo.item.dto.ItemPatchDto;
import com.example.demo.item.dto.ItemRequestDto;
import com.example.demo.user_item.domain.UserItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserItemMapper {
    UserItem itemRequestToItemUser(ItemRequestDto itemRequestDto);
}