package pp.project.vmm.endpoint.system.repository.custom;

import pp.project.vmm.endpoint.system.model.Sale;

import java.util.List;
import java.util.UUID;

public interface SaleRepositoryCustom {

    List<Sale> findNotArchived();

    List<Sale> findArchived();

    List<Sale> findByItemId(UUID id);

    List<Sale> findByMachineId(UUID id);
}
