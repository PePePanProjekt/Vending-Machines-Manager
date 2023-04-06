package pp.project.vmm.endpoint.system.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pp.project.vmm.endpoint.system.model.Holds;

import java.util.List;
import java.util.UUID;

@Repository
public class HoldsDAO implements DAO<Holds> {

    private EntityManager entityManager;

    @Autowired
    public HoldsDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public Holds findById(UUID id) {
        return entityManager.find(Holds.class, id);
    }

    @Override
    public List<Holds> findAll() {
        TypedQuery<Holds> query = entityManager.createQuery("FROM Holds", Holds.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void save(Holds object) {
        entityManager.persist(object);
    }

    @Override
    @Transactional
    public void update(Holds object) {
        entityManager.merge(object);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Holds holds = entityManager.find(Holds.class, id);
        entityManager.remove(holds);
    }
}
