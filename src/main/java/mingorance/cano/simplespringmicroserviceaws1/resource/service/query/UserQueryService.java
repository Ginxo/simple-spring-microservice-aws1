package mingorance.cano.simplespringmicroserviceaws1.resource.service.query;

import java.util.List;

import mingorance.cano.simplespringmicroserviceaws1.mapper.UserMapper;
import mingorance.cano.simplespringmicroserviceaws1.persistence.entity.User;
import mingorance.cano.simplespringmicroserviceaws1.persistence.entity.User_;
import mingorance.cano.simplespringmicroserviceaws1.persistence.repository.UserRepository;
import mingorance.cano.simplespringmicroserviceaws1.resource.criteria.UserCriteria;
import mingorance.cano.simplespringmicroserviceaws1.resource.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserQueryService extends QueryService<User> {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserQueryService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findByCriteria(UserCriteria criteria) {
        final Specification<User> specification = createSpecification(criteria);
        return userMapper.toDto(userRepository.findAll(specification));
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findByCriteria(UserCriteria criteria, Pageable page) {
        final Specification<User> specification = createSpecification(criteria);
        return userRepository.findAll(specification, page)
                .map(userMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(UserCriteria criteria) {
        final Specification<User> specification = createSpecification(criteria);
        return userRepository.count(specification);
    }

    private Specification<User> createSpecification(UserCriteria criteria) {
        Specification<User> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), User_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), User_.name));
            }
            if (criteria.getSurname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSurname(), User_.surname));
            }
        }
        return specification;
    }
}
