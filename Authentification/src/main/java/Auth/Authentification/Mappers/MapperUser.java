package Auth.Authentification.Mappers;

import Auth.Authentification.DTos.dtoUser;
import Auth.Authentification.Entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperUser {
    User ToUser(dtoUser dtouser);
    dtoUser Todto (User user);

}
