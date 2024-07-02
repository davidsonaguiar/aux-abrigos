package com.compass;

import com.compass.db.DatabaseConnectionException;
import com.compass.db.JpaConnector;
import jakarta.persistence.EntityManager;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = null;
        try {
            entityManager = JpaConnector.getEntityManager("aux-abrigos-persistence-unit");
            System.out.println("Conexão realizada com sucesso!");
        }
        catch (DatabaseConnectionException exception) {
            exception.printStackTrace();
        }
        finally {
            JpaConnector.closeEntityManager(entityManager);
            JpaConnector.closeEntityManagerFactory();
            System.out.println("Conexão encerrada!");
        }
    }
}