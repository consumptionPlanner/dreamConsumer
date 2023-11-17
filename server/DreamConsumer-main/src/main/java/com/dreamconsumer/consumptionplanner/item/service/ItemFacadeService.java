package com.dreamconsumer.consumptionplanner.item.service;

import com.dreamconsumer.consumptionplanner.item.domain.Item;
import com.dreamconsumer.consumptionplanner.image.service.FileSystemStorageService;
import com.dreamconsumer.consumptionplanner.member.service.MemberService;
import com.dreamconsumer.consumptionplanner.member_item.domain.MemberItem;
import com.dreamconsumer.consumptionplanner.member_item.service.MemberItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ItemFacadeService {

    private final ItemService itemService;
    private final MemberItemService memberItemService;
    private final MemberService memberService;
    private final FileSystemStorageService fileSystemStorageService;

    public ItemFacadeService(ItemService itemService, MemberItemService memberItemService, MemberService memberService, FileSystemStorageService fileSystemStorageService) {
        this.itemService = itemService;
        this.memberItemService = memberItemService;
        this.memberService = memberService;
        this.fileSystemStorageService = fileSystemStorageService;
    }

    @Transactional
    public void saveItemAndUserItem(Item item, MemberItem memberItem, Long userId) {
        itemService.saveItem(item);
        memberService.updateTier(userId, 1);
        memberItem.setItem(item);
        memberItem.setMember(memberService.getUser(userId));
        memberItemService.saveUserItem(memberItem);
    }

    @Transactional
    public void getItemDetails() {

    }

    @Transactional
    public void getAllItemDetails(Long memberId) {

    }

//    public API<List<ItemResponseDto>> getAllItems(Pageable pageable) {
//
//    }
}