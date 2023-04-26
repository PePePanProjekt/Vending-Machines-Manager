package pp.project.vmm.endpoint.system.repository.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import pp.project.vmm.endpoint.system.model.Contains;

import java.util.List;

@Repository
public class ContainsRepositoryCustomImplementation implements ContainsRepositoryCustom{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Contains> findNotArchived() {

        TypedQuery<Contains> query = entityManager.createQuery("SELECT c FROM Contains c WHERE c.archived = false", Contains.class);
        return query.getResultList();
    }

    @Override
    public List<Contains> findArchived() {

        TypedQuery<Contains> query = entityManager.createQuery("SELECT c FROM Contains c WHERE c.archived = true", Contains.class);
        return query.getResultList();
    }
}
