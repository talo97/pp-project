package com.ppproject.common;

import java.util.List;
import java.util.Optional;

public interface CommonService<E extends CommonEntity, K> {
    E save(E entity);
    E update(E entity);
    Optional<E> get(K id);
    List<E> getAll();
    void delete(K id);
    void delete(E entity);

}
