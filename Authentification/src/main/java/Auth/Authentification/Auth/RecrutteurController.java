package Auth.Authentification.Auth;

import Auth.Authentification.DTos.UserDto;
import Auth.Authentification.DTos.dtoUser;
import Auth.Authentification.Entity.User;
import Auth.Authentification.Mappers.MapperUser;
import Auth.Authentification.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recrutteur")
@RequiredArgsConstructor
public class RecrutteurController {
    @Autowired
    private UserService userService;
    private MapperUser mapperUser;


    @PostMapping("/validate/{userId}")
    public ResponseEntity<Object> validateUser(@PathVariable Long userId) {
        try {
            userService.processUserValidation(userId);
            return ResponseEntity.ok().body("{\"message\": \"Validation réussie et email envoyé\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Erreur lors de la validation de l'utilisateur : " + e.getMessage() + "\"}");
        }
    }
    @GetMapping("/allusers")
    public List<UserDto> getAllUser() {
        return userService.getAllUser();
    }
    @GetMapping("/allvalid")
    public List<UserDto> getAllRecruiterUsers() {
        return userService.getAllRecruiterUsers();
    }
    @GetMapping("/allNotvalid")
    public  List<UserDto> getAllUserNotvalid(){
        return  userService.getAllUserNotvalid();
    }



    @GetMapping("/{uuid}")
    public ResponseEntity<User> getUserByUUID(@PathVariable UUID uuid) {
        User user = userService.getUserByUUID(uuid);

        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping(value = "/{id}", consumes = { "multipart/form-data" })
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestPart(value = "dtouserjson", required = false) String dtouserjson,
            @RequestPart(value = "logoFile", required = false) MultipartFile logoFile
    ) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            dtoUser dtouser = objectMapper.readValue(dtouserjson, dtoUser.class);

            dtoUser updatedUserDto = userService.updateUser(id, dtouser, logoFile);
            System.out.println("abc"+ updatedUserDto);

            return ResponseEntity.ok(updatedUserDto);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur de lecture des données utilisateur: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé avec l'ID: " + id);
        }
    }



    @GetMapping("/users/valid/count")
    public long countValidUsers() {
        return userService.countValidUsers();
    }

    @GetMapping("/users/invalid/count")
    public long countInvalidUsers() {
        return userService.countInvalidUsers();
    }
}
