package pp.project.vmm.endpoint.system.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pp.project.vmm.endpoint.system.model.Batch;

import java.util.List;
import java.util.UUID;

@Repository
public class BatchDAO implements DAO<Batch> {

    private EntityManager entityManager;

    @Autowired
    public BatchDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public Batch findById(UUID id) {
        return entityManager.find(Batch.class, id);
    }

    @Override
    public List<Batch> findAll() {
        TypedQuery<Batch> query = entityManager.createQuery("FROM Batch", Batch.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void save(Batch object) {
        entityManager.persist(object);
    }

    @Override
    @Transactional
    public void update(Batch object) {
        entityManager.persist(object);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Batch batch = entityManager.find(Batch.class, id);
        entityManager.remove(batch);
    }
}
