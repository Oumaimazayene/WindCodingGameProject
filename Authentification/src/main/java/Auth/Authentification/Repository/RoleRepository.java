package Auth.Authentification.Repository;

import Auth.Authentification.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
public interface RoleRepository extends JpaRepository<Role,Long> {
    @Query("SELECT id FROM Role WHERE name = :name")
    Long findIdByName(@Param("name") String name);

    Optional<Object> findByName(String roleName);
}
