package Auth.Authentification.Repository;

import Auth.Authentification.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUuid(UUID uuid);
    long countByIsVerifiedTrue();

    long countByIsVerifiedFalse();

}
