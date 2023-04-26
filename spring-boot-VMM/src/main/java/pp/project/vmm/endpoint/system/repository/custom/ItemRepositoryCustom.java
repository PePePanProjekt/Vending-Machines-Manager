package pp.project.vmm.endpoint.system.repository.custom;

import pp.project.vmm.endpoint.system.model.Item;

import java.util.List;

public interface ItemRepositoryCustom {

    List<Item> findNotArchived();

    List<Item> findArchived();

}
