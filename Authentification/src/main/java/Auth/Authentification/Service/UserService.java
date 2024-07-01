package Auth.Authentification.Service;

import Auth.Authentification.DTos.UserDto;
import Auth.Authentification.DTos.dtoUser;
import Auth.Authentification.Entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface UserService {
    User getUserById(Long id);
    List<UserDto> getAllUser();
    void softDeleteUser(Long id);
    void processUserValidation(Long userId);
    List<UserDto> getAllRecruiterUsers();
    User getUserByUUID(UUID uuid);
    List<UserDto> getAllUserNotvalid();
    dtoUser updateUser(Long id, dtoUser dtouser, MultipartFile logoFile) throws IOException;
    long countInvalidUsers();
    long countValidUsers();
}
