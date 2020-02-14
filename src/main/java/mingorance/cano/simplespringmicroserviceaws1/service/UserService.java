package mingorance.cano.simplespringmicroserviceaws1.service;

import java.util.Optional;

import mingorance.cano.simplespringmicroserviceaws1.resource.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserDTO save(UserDTO user);

    Page<UserDTO> findAll(Pageable pageable);

    Optional<UserDTO> findOne(Long id);

    void delete(Long id);
}
