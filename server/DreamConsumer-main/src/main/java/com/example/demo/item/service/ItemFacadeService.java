package com.example.demo.item.service;

import com.example.demo.item.domain.Item;
import com.example.demo.item.image.FileSystemStorageService;
import com.example.demo.user.service.UserService;
import com.example.demo.user_item.domain.UserItem;
import com.example.demo.user_item.service.UserItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ItemFacadeService {

    private final ItemService itemService;
    private final UserItemService userItemService;
    private final UserService userService;
    private final FileSystemStorageService fileSystemStorageService;

    public ItemFacadeService(ItemService itemService, UserItemService userItemService, UserService userService, FileSystemStorageService fileSystemStorageService) {
        this.itemService = itemService;
        this.userItemService = userItemService;
        this.userService = userService;
        this.fileSystemStorageService = fileSystemStorageService;
    }

    @Transactional
    public void saveItemAndUserItem(Item item, UserItem userItem, MultipartFile image, Long userId) {

        item.setImageUrl(fileSystemStorageService.store(image, item.getName()));
        userItemService.saveUserItem(userItem);
        userService.updateTier(userId, 1);
        itemService.saveItem(item);
        userItemService.saveUserItem(userItem);
    }
}