package pp.project.vmm.endpoint.system.dao;

import java.util.List;
import java.util.UUID;

public interface DAO<T> {

    T findById(UUID id);

    List<T> findAll();

    void save(T object);

    void update(T object);

    void delete(UUID id);
}
