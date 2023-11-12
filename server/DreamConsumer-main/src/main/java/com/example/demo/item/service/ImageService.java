package com.example.demo.item.service;

import com.example.demo.item.domain.Item;
import com.example.demo.item.image.FileSystemStorageService;
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