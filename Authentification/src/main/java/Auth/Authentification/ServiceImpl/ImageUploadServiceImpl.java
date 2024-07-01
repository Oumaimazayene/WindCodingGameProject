package Auth.Authentification.ServiceImpl;


import Auth.Authentification.Service.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

        Path staticPath = Paths.get("Authentification/src/main/resources/images");

        if (!Files.exists(staticPath)) {
            Files.createDirectories(staticPath);
        }
        Path imagePath = staticPath.resolve(uniqueFileName);
        Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }


}

