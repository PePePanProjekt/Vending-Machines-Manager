package pp.project.vmm.endpoint.system.repository.custom;

import pp.project.vmm.endpoint.system.model.Contains;

import java.util.List;

public interface ContainsRepositoryCustom {

    List<Contains> findNotArchived();

    List<Contains> findArchived();

}
