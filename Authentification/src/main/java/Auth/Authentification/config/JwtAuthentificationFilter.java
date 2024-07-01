package Auth.Authentification.config;

import Auth.Authentification.Entity.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import reactor.util.annotation.NonNull;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthentificationFilter extends OncePerRequestFilter {
    private final JwtService jwtService ;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
     final String authHeader = request.getHeader("Authorization");
     final String jwt;
     final  String useremail;
     final String role;

     if (authHeader == null || authHeader.startsWith("Bearer ") ){
         filterChain.doFilter(request, response);
         return;
     }
     jwt = authHeader.substring(7);
     useremail = jwtService.extractUsername(jwt);
     role = jwtService.extractRole(jwt);


    }
}
