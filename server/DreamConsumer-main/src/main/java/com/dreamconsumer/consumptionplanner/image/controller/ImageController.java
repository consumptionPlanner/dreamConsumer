package com.dreamconsumer.consumptionplanner.image.controller;

import com.dreamconsumer.consumptionplanner.image.service.FileSystemStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("image")
public class ImageController {

    private final FileSystemStorageService fileSystemStorageService;

    public ImageController(FileSystemStorageService fileSystemStorageService) {
        this.fileSystemStorageService = fileSystemStorageService;
    }

    @PostMapping("")
    public ResponseEntity saveImage(@RequestParam("image") MultipartFile image) {
        String imageUrl = fileSystemStorageService.store(image, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_hhMMss")));
        return new ResponseEntity<String>(imageUrl, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity getImage(@RequestBody String imageUrl) {
        return new ResponseEntity<>(fileSystemStorageService.get(imageUrl), HttpStatus.OK);
    }

}
