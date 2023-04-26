package pp.project.vmm.endpoint.system.repository.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import pp.project.vmm.endpoint.system.model.VendingMachine;

import java.util.List;

@Repository
public class VendingMachineRepositoryCustomImplementation implements VendingMachineRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<VendingMachine> findNotArchived() {

        TypedQuery<VendingMachine> query = entityManager.createQuery("SELECT vm FROM VendingMachine vm WHERE vm.archived = false", VendingMachine.class);
        return query.getResultList();
    }

    @Override
    public List<VendingMachine> findArchived() {
        TypedQuery<VendingMachine> query = entityManager.createQuery("SELECT vm FROM VendingMachine vm WHERE vm.archived = true", VendingMachine.class);
        return query.getResultList();
    }
}
