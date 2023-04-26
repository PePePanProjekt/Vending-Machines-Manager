package pp.project.vmm.endpoint.system.repository.custom;

import pp.project.vmm.endpoint.system.model.Sale;

import java.util.List;

public interface SaleRepositoryCustom {

    List<Sale> findNotArchived();

    List<Sale> findArchived();
}
