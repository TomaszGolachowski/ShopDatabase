package com.app.tGolachowski.repository.generic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static com.app.tGolachowski.service.MenuImpl.process;

public class DbConnection {
    private static DbConnection ourInstance = new DbConnection();

    public static DbConnection getInstance() {
        return ourInstance;
    }

    private DbConnection() {

    }

    public EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("HBN");

    protected EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void close() {
        if (entityManagerFactory != null && !process) {
            entityManagerFactory.close();
        }
    }
}
