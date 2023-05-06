package pp.project.vmm.endpoint.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pp.project.vmm.endpoint.system.model.Holds;
import pp.project.vmm.endpoint.system.repository.custom.HoldsRepositoryCustom;

import java.util.UUID;

public interface HoldsRepository extends JpaRepository<Holds, UUID>, HoldsRepositoryCustom {

}
