package pp.project.vmm.endpoint.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pp.project.vmm.endpoint.system.model.Sales;

import java.util.UUID;

public interface SalesRepository extends JpaRepository<Sales, UUID> {
}
