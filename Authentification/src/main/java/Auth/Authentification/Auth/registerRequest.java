package Auth.Authentification.Auth;

import Auth.Authentification.DTos.RoleDto;
import Auth.Authentification.Entity.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class registerRequest {
    private String firstname;
    private String lastname;
    private  String email;
    private String numtel;
    private String societyName;
    private String Matricule_fiscale;

}
