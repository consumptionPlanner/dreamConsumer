package com.dreamconsumer.consumptionplanner.item.controller;

import com.dreamconsumer.consumptionplanner.item.domain.Item;
import com.dreamconsumer.consumptionplanner.item.dto.ItemRequestDto;
import com.dreamconsumer.consumptionplanner.item.mapper.ItemMapper;
import com.dreamconsumer.consumptionplanner.item.service.ItemFacadeService;
import com.dreamconsumer.consumptionplanner.item.service.ItemService;
import com.dreamconsumer.consumptionplanner.member_item.domain.MemberItem;
import com.dreamconsumer.consumptionplanner.member_item.mapper.MemberItemMapper;
import com.dreamconsumer.consumptionplanner.tag.mapper.TagMapper;
import com.dreamconsumer.consumptionplanner.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final ItemMapper itemMapper;
    private final ItemFacadeService itemFacadeService;
    private final MemberItemMapper memberItemMapper;
    private final TagService tagService;
    private final TagMapper tagMapper;

    @PostMapping("")
    public ResponseEntity saveItem(@RequestBody ItemRequestDto itemRequestDto){
        // 공동 구매 URL 생성 로직 필요
        Item item = itemMapper.itemRequestDtoToItem(itemRequestDto);
        MemberItem memberItem = memberItemMapper.itemRequestToMemberItem(itemRequestDto);
        // mapper에서 tag 객체로 변한 후 service에서 saveAll
        memberItem.setTags(tagMapper.stringsToTags(itemRequestDto.getTags(), memberItem));
        itemFacadeService.saveItemAndUserItem(item, memberItem, itemRequestDto.getUserId());
        return new ResponseEntity(itemMapper.ItemToItemResponseDto(item), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity getAllItem(@PageableDefault(size = 3, page = 0 , sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                     @RequestParam String filter,
                                     @PathVariable Long userId) {
        // select * from ITEM where ( select * from USER_ITEM where userId =? and 필터링)
        // filter = all -> 전체, personal -> item.groupPurchase== false, common -> item.groupPurchase == true, bookmark -> item.bookmark == true
//        API<List<ItemResponseDto>> listAPI = itemFacadeService.getAllItems(pageable);

        return new ResponseEntity(HttpStatus.OK);
    }
//    @GetMapping("")
//    public ResponseEntity getAll(@PageableDefault(size=20, sort="id",direction = Sort.Direction.DESC) Pageable pageable){
//        API<List<ItemResponseDto>> listAPI = itemService.getAllItems(pageable);
//        return new ResponseEntity(listAPI, HttpStatus.ACCEPTED);
//    }
}
