package pp.project.vmm.endpoint.system.repository.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import pp.project.vmm.endpoint.system.model.Item;

import java.util.List;

@Repository
public class ItemRepositoryCustomImplementation implements ItemRepositoryCustom{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Item> findNotArchived() {

        TypedQuery<Item> query = entityManager.createQuery("SELECT i FROM Item i WHERE i.archived = false", Item.class);
        return query.getResultList();
    }

    @Override
    public List<Item> findArchived() {

        TypedQuery<Item> query = entityManager.createQuery("SELECT i FROM Item i WHERE i.archived = true", Item.class);
        return query.getResultList();
    }
}
