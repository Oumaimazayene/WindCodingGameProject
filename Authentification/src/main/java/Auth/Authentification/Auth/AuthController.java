package Auth.Authentification.Auth;

import Auth.Authentification.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    private  final AuthenticationService authenticationService;
    private final UserRepository repository;



    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody registerRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));

    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestParam UUID uuid,
                                            @RequestParam String oldPassword,
                                            @RequestParam String newPassword) {
        try {
            authenticationService.changePassword(uuid, oldPassword, newPassword);
            return ResponseEntity.ok().body(Map.of("success", true, "message", "Mot de passe modifié avec succès"));
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la modification du mot de passe", e);
        }
    }
    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        try {
            authenticationService.resetPassword(email, newPassword);
            return ResponseEntity.ok("Mot de passe réinitialisé avec succès");
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé avec l'email spécifié", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la réinitialisation du mot de passe", e);
        }
    }


    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean emailExists = repository.findByEmail(email).isPresent();
        return ResponseEntity.ok(emailExists);
    }
}
