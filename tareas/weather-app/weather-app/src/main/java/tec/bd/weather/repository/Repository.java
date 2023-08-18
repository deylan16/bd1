package tec.bd.weather.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {

    /**
     * Find item by Id
     * @param id
     * @return Item
     */
    // "nullable"
    Optional<T> findById(ID id);
    List<T> findAll();
    void save(T t);

    void delete(ID id);

    T update(T source);
}
