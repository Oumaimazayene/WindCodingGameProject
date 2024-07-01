package Auth.Authentification.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageUploadService
{
    String saveImage(MultipartFile imageFile) throws IOException;

}
