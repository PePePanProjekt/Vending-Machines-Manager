package pp.project.vmm.endpoint.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pp.project.vmm.endpoint.system.model.Sale;

import java.util.UUID;

public interface SaleRepository extends JpaRepository<Sale, UUID> {
}
