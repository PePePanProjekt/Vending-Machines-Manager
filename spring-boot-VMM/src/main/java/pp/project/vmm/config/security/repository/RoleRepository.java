package pp.project.vmm.config.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pp.project.vmm.config.security.model.ERole;
import pp.project.vmm.config.security.model.Role;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findRoleByName(ERole name);
    Boolean existsRoleByName(ERole name);
}
