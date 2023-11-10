package com.example.demo.item.image;

import org.springframework.web.multipart.MultipartFile;

public interface StoreService2 {
    public String store(MultipartFile file, String name);
}