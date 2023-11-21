package com.dreamconsumer.consumptionplanner.image.controller;

import com.dreamconsumer.consumptionplanner.image.dto.ImageRequestDto;
import com.dreamconsumer.consumptionplanner.image.service.S3ImageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final S3ImageService s3ImageService;

    public ImageController(S3ImageService s3ImageService) {
        this.s3ImageService = s3ImageService;
    }

    @PostMapping("")
    public ResponseEntity saveImage(@RequestParam("image") MultipartFile image) throws IOException {
        String imageUrl = s3ImageService.saveImage(image);
        return new ResponseEntity<String>(imageUrl, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity getImage(@RequestBody ImageRequestDto imageRequestDto) throws Exception {
        Resource imageResource = s3ImageService.getImage(imageRequestDto.getImageUrl());
        return ResponseEntity.ok()
//                .contentType(MediaType.IMAGE_JPEG)
                .body(imageResource);
    }

}
