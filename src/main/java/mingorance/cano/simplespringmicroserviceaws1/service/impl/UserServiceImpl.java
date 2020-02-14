package mingorance.cano.simplespringmicroserviceaws1.service.impl;

import java.util.Optional;

import mingorance.cano.simplespringmicroserviceaws1.mapper.UserMapper;
import mingorance.cano.simplespringmicroserviceaws1.persistence.repository.UserRepository;
import mingorance.cano.simplespringmicroserviceaws1.resource.dto.UserDTO;
import mingorance.cano.simplespringmicroserviceaws1.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO save(UserDTO user) {
        return this.userMapper.toDto(this.userRepository.save(this.userMapper.toEntity(user)));
    }

    @Override
    public Page<UserDTO> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable)
                .map(this.userMapper::toDto);
    }

    @Override
    public Optional<UserDTO> findOne(Long id) {
        return this.userRepository.findById(id)
                .map(this.userMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }
}
