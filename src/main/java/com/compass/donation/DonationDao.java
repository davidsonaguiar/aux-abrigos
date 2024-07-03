package com.compass.donation;

import com.compass.common.dao.GenericDao;
import jakarta.persistence.EntityManager;

public class DonationDao extends GenericDao<DonationEntity, Long> {
    public DonationDao(EntityManager entityManager, Class<DonationEntity> classEntity) {
        super(entityManager, classEntity);
    }
}
