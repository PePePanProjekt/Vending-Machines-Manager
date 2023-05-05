package pp.project.vmm.endpoint.system.repository.custom;

import pp.project.vmm.endpoint.system.model.Holds;

import java.util.List;
import java.util.UUID;

public interface HoldsRepositoryCustom {

    List<Holds> findNotArchived();

    List<Holds> findArchived();

    List<Holds> findByItemId(UUID id);

}
