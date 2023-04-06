package pp.project.vmm.endpoint.system.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pp.project.vmm.endpoint.system.model.VendingMachine;

import java.util.List;
import java.util.UUID;

@Repository
public class VendingMachineDAO implements DAO<VendingMachine> {

    private EntityManager entityManager;

    @Autowired
    public VendingMachineDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public VendingMachine findById(UUID id) {
        return entityManager.find(VendingMachine.class, id);
    }

    @Override
    public List<VendingMachine> findAll() {
        TypedQuery<VendingMachine> query = entityManager.createQuery("FROM VendingMachine", VendingMachine.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void save(VendingMachine object) {
        entityManager.persist(object);
    }

    @Override
    @Transactional
    public void update(VendingMachine object) {
        entityManager.merge(object);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        VendingMachine vendingMachine = entityManager.find(VendingMachine.class, id);
        entityManager.remove(vendingMachine);
    }
}
