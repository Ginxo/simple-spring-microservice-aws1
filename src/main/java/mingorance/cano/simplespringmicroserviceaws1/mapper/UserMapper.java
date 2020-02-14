package mingorance.cano.simplespringmicroserviceaws1.mapper;

import mingorance.cano.simplespringmicroserviceaws1.persistence.entity.User;
import mingorance.cano.simplespringmicroserviceaws1.resource.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper extends EntityMapper<UserDTO, User> {

    default User fromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
