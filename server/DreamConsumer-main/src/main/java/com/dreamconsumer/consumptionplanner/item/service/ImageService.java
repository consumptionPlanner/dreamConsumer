package com.dreamconsumer.consumptionplanner.item.service;

import com.dreamconsumer.consumptionplanner.item.domain.Item;
import com.dreamconsumer.consumptionplanner.image.service.FileSystemStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {
    private final FileSystemStorageService fileSystemStorageService;
    private final ItemService itemService;

    public String saveItemImage(Item item, MultipartFile image){
        String imageUrl = fileSystemStorageService.store(image,item.getName());
        return imageUrl;
    }
}