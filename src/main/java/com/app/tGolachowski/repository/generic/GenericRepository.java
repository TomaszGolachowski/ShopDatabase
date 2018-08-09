package com.app.tGolachowski.repository.generic;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T> {

    T addOrUpdate(T t);
    void delete(Long id);
    Optional<T> findOneById(Long id);
    List<T> findAll();
    void deleteAll();

}
