package com.compass.donation;

import com.compass.common.dao.GenericDao;
import com.compass.common.exception.DaoException;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DonationDao extends GenericDao<DonationEntity, Long> {
    public DonationDao(EntityManager entityManager, Class<DonationEntity> classEntity) {
        super(entityManager, classEntity);
    }

    public DonationEntity updateCenter(DonationEntity donationEntity) {
        entityManager.getTransaction().begin();
        DonationEntity donationEntityFound = entityManager.find(DonationEntity.class, donationEntity.getId());
        donationEntityFound.setCenter(donationEntity.getCenter());
        entityManager.getTransaction().commit();
        return donationEntityFound;
    }

    public List<DonationEntity> saveMany(List<DonationEntity> donations) {
        try {
            entityManager.getTransaction().begin();
            for (DonationEntity donation : donations) {
                entityManager.persist(donation);
            }
            entityManager.getTransaction().commit();
            return donations;
        }
        catch (Exception exception) {
            entityManager.getTransaction().rollback();
            throw new DaoException("Erro ao salvar doações.", exception);
        }
    }
}
