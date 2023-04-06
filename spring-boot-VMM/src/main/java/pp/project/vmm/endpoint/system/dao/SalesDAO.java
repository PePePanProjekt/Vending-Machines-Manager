package pp.project.vmm.endpoint.system.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pp.project.vmm.endpoint.system.model.Sales;

import java.util.List;
import java.util.UUID;

@Repository
public class SalesDAO implements DAO<Sales> {

    private EntityManager entityManager;

    @Autowired
    public SalesDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public Sales findById(UUID id) {
        return entityManager.find(Sales.class, id);
    }

    @Override
    public List<Sales> findAll() {
        TypedQuery<Sales> query = entityManager.createQuery("FROM Sales", Sales.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void save(Sales object) {
        entityManager.persist(object);
    }

    @Override
    @Transactional
    public void update(Sales object) {
        entityManager.merge(object);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Sales sales = entityManager.find(Sales.class, id);
        entityManager.remove(sales);
    }
}
