package pp.project.vmm.endpoint.system.repository.custom;

import pp.project.vmm.endpoint.system.model.Batch;

import java.util.List;

public interface BatchRepositoryCustom {

    List<Batch> findNotArchived();

    List<Batch> findArchived();

}
