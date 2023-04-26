package pp.project.vmm.endpoint.system.repository.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import pp.project.vmm.endpoint.system.model.Sale;

import java.util.List;

@Repository
public class SaleRepositoryCustomImpl implements SaleRepositoryCustom{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Sale> findNotArchived() {

        TypedQuery<Sale> query = entityManager.createQuery("SELECT s FROM Sale s WHERE s.archived = false", Sale.class);
        return query.getResultList();
    }

    @Override
    public List<Sale> findArchived() {

        TypedQuery<Sale> query = entityManager.createQuery("SELECT s FROM Sale s WHERE s.archived = true", Sale.class);
        return query.getResultList();
    }
}
