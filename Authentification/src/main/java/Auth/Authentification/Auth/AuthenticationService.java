package Auth.Authentification.Auth;

import Auth.Authentification.Entity.Role;
import Auth.Authentification.Entity.User;
import Auth.Authentification.Repository.RoleRepository;
import Auth.Authentification.Repository.UserRepository;
import Auth.Authentification.config.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 12;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);


    public AuthentificationResponce register(registerRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        Long recruiterRoleId = roleRepository.findIdByName("recruteur");

            var userBuilder = User.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .Matricule_fiscale(request.getMatricule_fiscale())
                    .numtel(request.getNumtel())
                    .societyName(request.getSocietyName());
            var user = userBuilder.build();
        Optional<Role> role= roleRepository.findById(recruiterRoleId);
        user.setRole(role.get());


        repository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthentificationResponce.builder()
                    .token(jwtToken)
                    .build();
        }

    public AuthentificationResponce authenticate(AuthenticationRequest request) {
authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        )
);
          var user = repository.findByEmail(request.getEmail()).orElseThrow();
        if (!user.isVerified()) {
            throw new RuntimeException("L'utilisateur n'est pas validé");
        }
        var jwtToken = jwtService.generateToken(user);
        return AuthentificationResponce.builder()
                .token(jwtToken)
                .build();

    }

    public void changePassword(UUID uuid, String oldPassword, String newPassword) {
        User user = repository.findByUuid(uuid)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'UUID : " + uuid));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BadCredentialsException("Ancien mot de passe incorrect");
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setDeleted(true);
        repository.save(user);
        logger.info("Mot de passe modifié avec succès pour l'utilisateur avec UUID : {}", uuid);
    }

    public void resetPassword(String email, String newPassword) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        repository.save(user);

        logger.info("Mot de passe réinitialisé avec succès pour l'utilisateur : {}", email);
    }



}
