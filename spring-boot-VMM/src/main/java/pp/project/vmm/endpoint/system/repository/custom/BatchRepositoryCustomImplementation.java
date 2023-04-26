package pp.project.vmm.endpoint.system.repository.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import pp.project.vmm.endpoint.system.model.Batch;

import java.util.List;

@Repository
public class BatchRepositoryCustomImplementation implements BatchRepositoryCustom{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Batch> findNotArchived() {

        TypedQuery<Batch> query = entityManager.createQuery("SELECT b FROM Batch b WHERE b.archived = false", Batch.class);
        return query.getResultList();
    }

    @Override
    public List<Batch> findArchived() {

        TypedQuery<Batch> query = entityManager.createQuery("SELECT b FROM Batch b WHERE b.archived = true", Batch.class);
        return query.getResultList();
    }
}
