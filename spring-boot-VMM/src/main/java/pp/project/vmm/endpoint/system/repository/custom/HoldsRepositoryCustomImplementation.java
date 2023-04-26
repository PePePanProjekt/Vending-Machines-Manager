package pp.project.vmm.endpoint.system.repository.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import pp.project.vmm.endpoint.system.model.Holds;

import java.util.List;

@Repository
public class HoldsRepositoryCustomImplementation implements HoldsRepositoryCustom{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Holds> findNotArchived() {

        TypedQuery<Holds> query = entityManager.createQuery("SELECT h FROM Holds h WHERE h.archived = false", Holds.class);
        return query.getResultList();
    }

    @Override
    public List<Holds> findArchived() {

        TypedQuery<Holds> query = entityManager.createQuery("SELECT h FROM Holds h WHERE h.archived = true", Holds.class);
        return query.getResultList();
    }
}
