package pp.project.vmm.endpoint.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pp.project.vmm.endpoint.system.model.Batch;

import java.util.UUID;

public interface BatchRepository extends JpaRepository<Batch, UUID> {
}
