package pp.project.vmm.endpoint.system.repository.custom;

import pp.project.vmm.endpoint.system.model.Holds;

import java.util.List;

public interface HoldsRepositoryCustom {

    List<Holds> findNotArchived();

    List<Holds> findArchived();

}
