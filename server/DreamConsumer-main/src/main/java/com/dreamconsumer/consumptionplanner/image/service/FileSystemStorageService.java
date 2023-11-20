package com.dreamconsumer.consumptionplanner.image.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.*;

@Slf4j
@Service
public class FileSystemStorageService implements StoreService2 {
    //    private final Path rootLocation = Paths.get("/Users/jangtaehwan/file");
    private final Path rootLocation = Paths.get(System.getProperty("member.home"), "file");
    @Override
    public String store(MultipartFile file,String name) {
        String storedFilePath = null;
        try {
            if (file.isEmpty()) { // 20번째 라인
                throw new FileSystemNotFoundException("Failed to store empty file.");
            }
            String newDirectoryName = String.valueOf(name);
            Path newDirectoryPath = this.rootLocation.resolve(newDirectoryName);
            if (!Files.exists(newDirectoryPath)) {
                Files.createDirectories(newDirectoryPath);
            }
            Path destinationFile = newDirectoryPath.resolve(
                    Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(newDirectoryPath.toAbsolutePath())) {
                // This is a security check
                throw new FileSystemNotFoundException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                log.info("#stored");
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                storedFilePath = destinationFile.toString();
            }
        } catch (IOException e) {
            throw new FileSystemNotFoundException("Failed to store file.");
        }
        return storedFilePath.replace("\\", "\\\\");
    }

    @Override
    public Resource get(String filePath) {
        try {
            System.out.println("filePath + " + filePath);
            Path file = Paths.get(filePath.replace("\\\\", "\\"));
            System.out.println("file" + file);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileSystemNotFoundException("Could not read file: " + filePath);
            }
        } catch (MalformedURLException e) {
            throw new FileSystemNotFoundException("Error: " + e.getMessage());
        }
    }

}