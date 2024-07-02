package com.compass.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaConnector {
    private static EntityManagerFactory entityManagerFactory;

    public static EntityManager getEntityManager(String persistenceUnit) {
        try {
            if(entityManagerFactory != null) return entityManagerFactory.createEntityManager();
            entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
            return entityManagerFactory.createEntityManager();
        }
        catch (Exception exception) {
            throw new DatabaseConnectionException("Erro ao iniciar a conexão com o banco de dados!");
        }
    }

    public static void closeEntityManagerFactory() {
        if(entityManagerFactory != null && entityManagerFactory.isOpen())
            entityManagerFactory.close();
    }

    public static void closeEntityManager(EntityManager entityManager) {
        if(entityManager != null && entityManager.isOpen())
            entityManager.close();
    }
}
