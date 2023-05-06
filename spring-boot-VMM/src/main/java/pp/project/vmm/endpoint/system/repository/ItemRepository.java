package pp.project.vmm.endpoint.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pp.project.vmm.endpoint.system.model.Item;
import pp.project.vmm.endpoint.system.repository.custom.ItemRepositoryCustom;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID>, ItemRepositoryCustom {

}
