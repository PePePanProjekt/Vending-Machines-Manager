package pp.project.vmm.endpoint.system.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pp.project.vmm.endpoint.system.model.Contains;

import java.util.List;
import java.util.UUID;

@Repository
public class ContainsDAO implements DAO<Contains> {

    private EntityManager entityManager;

    @Autowired
    public ContainsDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public Contains findById(UUID id) {
        return entityManager.find(Contains.class, id);
    }

    @Override
    public List<Contains> findAll() {
        TypedQuery<Contains> query = entityManager.createQuery("FROM Contains", Contains.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void save(Contains object) {
        entityManager.persist(object);
    }

    @Override
    @Transactional
    public void update(Contains object) {
        entityManager.merge(object);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Contains contains = entityManager.find(Contains.class, id);
        entityManager.remove(contains);
    }
}
