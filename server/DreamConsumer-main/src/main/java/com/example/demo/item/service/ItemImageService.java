package com.example.demo.item.service;

import com.example.demo.item.domain.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemImageService {
    private final FileSystemStorageService fileSystemStorageService;
    private final ItemService itemService;

    public void registerPost(Item item, MultipartFile image){
        String uri = fileSystemStorageService.store(image,item.getName());
        item.setImageUrl(uri);
        itemService.savaItem(item);
    }
}