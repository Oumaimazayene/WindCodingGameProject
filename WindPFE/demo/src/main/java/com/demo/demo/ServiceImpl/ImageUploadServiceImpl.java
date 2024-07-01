package com.demo.demo.ServiceImpl;

import com.demo.demo.Service.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.UUID;

@Service
public class ImageUploadServiceImpl  implements ImageUploadService {


    @Autowired
    private ResourceLoader resourceLoader;

@Override
public String saveImage(MultipartFile imageFile) throws IOException {
    String originalFileName = imageFile.getOriginalFilename();
    String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;
    String imagePathString = "/images/" + uniqueFileName;

    Path staticPath = Paths.get("WindPFE/demo/src/main/resources/images");

    if (!Files.exists(staticPath)) {
        Files.createDirectories(staticPath);
    }
    Path imagePath = staticPath.resolve(uniqueFileName);
    Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

    return uniqueFileName;
}
    @Override
    public String convertImageToBase64(String imagePath) throws IOException {
        byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
        return Base64.getEncoder().encodeToString(imageBytes);
    }
}

