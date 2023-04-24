package pp.project.vmm.endpoint.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pp.project.vmm.endpoint.system.model.Contains;

import java.util.UUID;

public interface ContainsRepository extends JpaRepository<Contains, UUID> {
    
}
