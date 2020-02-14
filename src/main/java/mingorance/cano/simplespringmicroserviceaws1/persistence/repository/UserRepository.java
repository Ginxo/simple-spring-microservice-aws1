package mingorance.cano.simplespringmicroserviceaws1.persistence.repository;

import mingorance.cano.simplespringmicroserviceaws1.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>,
                                        JpaSpecificationExecutor<User> {

}
