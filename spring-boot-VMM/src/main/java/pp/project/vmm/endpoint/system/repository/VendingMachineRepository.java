package pp.project.vmm.endpoint.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pp.project.vmm.endpoint.system.model.VendingMachine;
import pp.project.vmm.endpoint.system.repository.custom.VendingMachineRepositoryCustom;

import java.util.UUID;

public interface VendingMachineRepository extends JpaRepository<VendingMachine, UUID>, VendingMachineRepositoryCustom {

}
