package pp.project.vmm.endpoint.system.repository.custom;

import pp.project.vmm.endpoint.system.model.VendingMachine;

import java.util.List;

public interface VendingMachineRepositoryCustom {

    List<VendingMachine> findNotArchived();

    List<VendingMachine> findArchived();
}
