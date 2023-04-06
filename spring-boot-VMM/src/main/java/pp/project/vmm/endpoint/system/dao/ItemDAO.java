package pp.project.vmm.endpoint.system.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pp.project.vmm.endpoint.system.model.Item;

import java.util.List;
import java.util.UUID;

@Repository
public class ItemDAO implements DAO<Item> {

    private EntityManager entityManager;

    @Autowired
    public ItemDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public Item findById(UUID id) {
        return entityManager.find(Item.class, id);
    }

    @Override
    public List<Item> findAll() {
        TypedQuery<Item> query = entityManager.createQuery("FROM Item", Item.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void save(Item object) {
        entityManager.persist(object);
    }

    @Override
    @Transactional
    public void update(Item object) {
        entityManager.merge(object);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Item item = entityManager.find(Item.class, id);
        entityManager.remove(item);
    }
}
