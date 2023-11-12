package com.example.demo.item.controller;

import com.example.demo.item.domain.Item;
import com.example.demo.item.dto.ItemRequestDto;
import com.example.demo.item.dto.ItemResponseDto;
import com.example.demo.item.image.FileSystemStorageService;
import com.example.demo.item.mapper.ItemMapper;
import com.example.demo.item.pagenation.API;
import com.example.demo.item.service.ItemFacadeService;
import com.example.demo.item.service.ItemService;
import com.example.demo.user.service.UserService;
import com.example.demo.user_item.domain.UserItem;
import com.example.demo.user_item.mapper.UserItemMapper;
import com.example.demo.user_item.service.UserItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final ItemMapper itemMapper;
    private final ItemFacadeService itemFacadeService;
    private final UserItemMapper userItemMapper;

    @PostMapping("")
    public ResponseEntity saveItem(@RequestPart("image") MultipartFile image,
                                   @RequestPart("data") ItemRequestDto itemRequestDto){
        // 공동 구매 URL 생성 로직 필요
        System.out.println(image);
        Item item = itemMapper.itemRequestDtoToItem(itemRequestDto);
        UserItem userItem = userItemMapper.itemRequestToItemUser(itemRequestDto);
        itemFacadeService.saveItemAndUserItem(item, userItem, image, itemRequestDto.getUserId());
        return new ResponseEntity(itemMapper.ItemToItemResponseDto(item), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity getAll(@PageableDefault(size=20, sort="id",direction = Sort.Direction.DESC) Pageable pageable){
        API<List<ItemResponseDto>> listAPI = itemService.getAllItems(pageable);
        return new ResponseEntity(listAPI, HttpStatus.ACCEPTED);
    }
}
