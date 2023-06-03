package pp.project.vmm.config.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pp.project.vmm.config.security.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserByUsername(String username);
    Boolean existsByUsername(String username);
}
