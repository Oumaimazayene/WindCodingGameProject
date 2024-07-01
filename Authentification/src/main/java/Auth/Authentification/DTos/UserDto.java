package Auth.Authentification.DTos;

import lombok.Data;

@Data
public class UserDto {
    private  Long id ;
    private String firstname;
    private String lastname;
    private  String email;
    private String password;
    private String societyName;
    private String numtel;
    private String Logo;
    private String Matricule_fiscale;
    private boolean isVerified;
    private boolean isDeleted ;







}
