package com.dreamconsumer.consumptionplanner.image.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StoreService2 {
    public String store(MultipartFile file, String name);

    public Resource get(String filePath);
}