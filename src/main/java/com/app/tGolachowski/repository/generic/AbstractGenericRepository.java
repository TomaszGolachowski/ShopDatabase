package com.app.tGolachowski.repository.generic;

import com.app.tGolachowski.exceptions.MyException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public class AbstractGenericRepository<T> implements GenericRepository<T> {

    private EntityManagerFactory entityManagerFactory = DbConnection.getInstance().entityManagerFactory;
    private Class<T> entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    public T addOrUpdate(T t) {
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            entityManager.merge(t); // jesli jest nowy obiekt to go dodaje, jesli juz istnieje update po id
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new MyException("GENERIC_REPOSITORY: ADD/UPDATE ENTITY ERROR", LocalDateTime.now());
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return t;
    }

    @Override
    public void delete(Long id) {

        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            entityManager.remove(id);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new MyException("GENERIC_REPOSITORY: DELETE ENTITY ERROR", LocalDateTime.now());
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

    }

    @Override
    public Optional<T> findOneById(Long id) {

        Optional<T> item = Optional.empty();

        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            item = Optional.of(entityManager.find(entityClass, id));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new MyException("GENERIC_REPOSITORY: FIND ENTITY ERROR", LocalDateTime.now());
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return item;
    }

    @Override
    public List<T> findAll() {
        List<T> items = null;

        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            items = entityManager
                    .createQuery("select t from " + entityClass.getCanonicalName() + " t")
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new MyException("GENERIC_REPOSITORY: FIND ALL ENTITIES ERROR", LocalDateTime.now());
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return items;
    }

    @Override
    public void deleteAll() {
        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();

            List<T> items = entityManager.createQuery("select i from " + entityClass.getCanonicalName() + " i ", entityClass).getResultList();

            if (items != null) {
                for (T item : items) {
                    entityManager.remove(item);
                }
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new MyException("GENERIC_REPOSITORY: DELETE ALL ENTITIES ERROR", LocalDateTime.now());
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}

