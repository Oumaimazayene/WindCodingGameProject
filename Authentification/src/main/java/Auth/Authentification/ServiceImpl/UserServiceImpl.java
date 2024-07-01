package Auth.Authentification.ServiceImpl;

import Auth.Authentification.DTos.UserDto;
import Auth.Authentification.DTos.dtoUser;
import Auth.Authentification.Entity.User;
import Auth.Authentification.Mappers.MapperUser;
import Auth.Authentification.Mappers.UserMapp;
import Auth.Authentification.Repository.RoleRepository;
import Auth.Authentification.Repository.UserRepository;
import Auth.Authentification.Service.ImageUploadService;
import Auth.Authentification.Service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private  final UserRepository userRepository;
    private final UserMapp userMapp;
    private final  EmailServiceImpl emailService;
    private final PasswordEncoder passwordEncoder;
    private  final RoleRepository roleRepository;
    private  final ImageUploadService imageUploadService;
    private final MapperUser mapperUser;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 12;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    @Override
    public List<UserDto> getAllUser() {
        return userRepository.findAll().stream()
                .map(userMapp::ToUserDTo)
                .collect(Collectors.toList());
    }



   @Override
   public dtoUser updateUser(Long id, dtoUser dtouser, MultipartFile logoFile) throws IOException {
       Optional<User> optionalUser = userRepository.findById(id);
       User existingUser = optionalUser.orElseThrow(() -> new RuntimeException("L'utilisateur avec l'ID " + id + " n'existe pas"));
       if (dtouser.getFirstname() != null && !dtouser.getFirstname().isEmpty()) {
           existingUser.setFirstname(dtouser.getFirstname());
       }
       if (dtouser.getLastname() != null && !dtouser.getLastname().isEmpty()) {
           existingUser.setLastname(dtouser.getLastname());
       }
       if (dtouser.getSocietyName() != null && !dtouser.getSocietyName().isEmpty()) {
           existingUser.setSocietyName(dtouser.getSocietyName());
       }

       if (dtouser.getNumtel() != null && !dtouser.getNumtel().isEmpty()) {
           existingUser.setNumtel(dtouser.getNumtel());
       }
       if (dtouser.getMatricule_fiscale() != null && !dtouser.getMatricule_fiscale().isEmpty()) {
           existingUser.setMatricule_fiscale(dtouser.getMatricule_fiscale());
       }

       if (logoFile != null && !logoFile.isEmpty()) {
           String logoImagePath = imageUploadService.saveImage(logoFile);
           existingUser.setLogo(logoImagePath);
       }


       User updatedUser = userRepository.save(existingUser);
       return mapperUser.Todto(updatedUser);
   }

    @Override
    public void softDeleteUser(Long id) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setDeleted(true);
                userRepository.save(user);
                System.out.println("User with ID: " + id + " soft-deleted successfully");
            } else {
                System.out.println("User with ID " + id + " not found");
                // Handle not found scenario
            }
        } catch (Exception e) {
            System.err.println("Error soft-deleting User");
            throw e;
        }
    }



    @Override

    public void processUserValidation(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);



        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (!user.isVerified()) {
                user.setVerified(true);
                String temporaryPassword = generateTemporaryPassword();

                sendValidationEmail(user, temporaryPassword);
                user.setPassword(passwordEncoder.encode(temporaryPassword));
                userRepository.save(user);
            } else {
                System.out.println("l'utilisateur  est déja verifier ");            }
        } else {
            System.out.println("utilisateur avec l'id "+userId+"n'est pas trouvé");        }
    }

    private void sendValidationEmail(User user, String temporaryPassword) {
        String subject = "Validation de Compte WindTestHub";
        String body = "Cher recruteur,\n\n"
                + "Votre compte a été validé avec succès. Nous vous remercions de votre inscription.\n"
                + "Veuillez trouver ci-dessous les détails de votre compte :\n\n"
                + "Nom: " + user.getLastname() + "\n"
                + "Prénom: " + user.getFirstname() + "\n"
                + "E-mail: " + user.getEmail() + "\n"
                + "Mot de passe temporaire: " + temporaryPassword + "\n\n"
                + "Veuillez changer votre mot de passe dès que possible après la connexion.\n\n"
                + "Bienvenue dans notre communauté !\n\n"
                + "Cordialement,\n"
                + "L'équipe de Wind Consulting Tunisia";
        emailService.sendEmail(user.getEmail(), subject, body);
    }
    private String generateTemporaryPassword() {
        StringBuilder stringBuilder = new StringBuilder(PASSWORD_LENGTH);
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int randomIndex = secureRandom.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }
    @Override
    public List<UserDto> getAllRecruiterUsers() {
        List<User> users = userRepository.findAll();

        List<User> recruiterUsers = users.stream()
                .filter(user -> user.getRole() != null && user.getRole().getName().equals("recruteur") && user.isVerified())
                .collect(Collectors.toList());

        return recruiterUsers.stream()
                .map(userMapp::ToUserDTo)
                .collect(Collectors.toList());
    }
    @Override
    public List<UserDto> getAllUserNotvalid(){
        List<User> users = userRepository.findAll();
        List<User> NotValid =users.stream()
                .filter(user -> user.getRole() !=null && user.getRole().getName().equals("recruteur") && !user.isVerified())
                .collect(Collectors.toList());
        return  NotValid.stream()
                .map(userMapp::ToUserDTo)
                .collect(Collectors.toList());
    }
    @Override
    public User getUserByUUID(UUID uuid) {
        Optional<User> userOptional = userRepository.findByUuid(uuid);
        if(userOptional.isPresent()) {
            return userOptional.get();
        } else {
            return null;
        }
    }
    @Override
    public long countInvalidUsers() {
        return userRepository.countByIsVerifiedFalse();
    }
    @Override
    public long countValidUsers() {
        return userRepository.countByIsVerifiedTrue();
    }






}
