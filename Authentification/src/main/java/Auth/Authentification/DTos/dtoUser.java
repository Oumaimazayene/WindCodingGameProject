package Auth.Authentification.DTos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)

public class dtoUser {
    private Long id;
    private String firstname;
    private String lastname;
    private String societyName;
    private String numtel;
    private String logo;
    private String matricule_fiscale;
}
