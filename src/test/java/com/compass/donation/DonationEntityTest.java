package com.compass.donation;

import com.compass.center.CenterEntity;
import com.compass.item.ItemEntity;
import com.compass.util.UtilConstraintViolation;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

public class DonationEntityTest {
    private DonationEntity donation;
    private UtilConstraintViolation<DonationEntity> violations;

    @BeforeEach
    public void setUp() {
        donation = new DonationEntity();
        violations = new UtilConstraintViolation<>();
    }

    @Test
    public void testValid() {
        donation.setId(1L);
        donation.setDate(new Date());
        donation.setCenter(new CenterEntity());
        donation.setItems(List.of(new ItemEntity()));
        assertTrue(violations.getConstraintViolations(donation).isEmpty());
    }

    @Test
    public void testDateNull() {
        donation.setId(1L);
        donation.setDate(null);
        donation.setCenter(new CenterEntity());
        donation.setItems(List.of(new ItemEntity()));
        assertFalse(violations.getConstraintViolations(donation).isEmpty());
    }

    @Test
    public void testDateFuture() {
        donation.setId(1L);
        donation.setDate(new Date(System.currentTimeMillis() + 100000));
        donation.setCenter(new CenterEntity());
        donation.setItems(List.of(new ItemEntity()));
        assertFalse(violations.getConstraintViolations(donation).isEmpty());
    }

    @Test
    public void testCenterNull() {
        donation.setId(1L);
        donation.setDate(new Date());
        donation.setCenter(null);
        donation.setItems(List.of(new ItemEntity()));
        assertFalse(violations.getConstraintViolations(donation).isEmpty());
    }

    @Test
    public void testItemsEmpty() {
        donation.setId(1L);
        donation.setDate(new Date());
        donation.setCenter(new CenterEntity());
        donation.setItems(List.of());
        assertFalse(violations.getConstraintViolations(donation).isEmpty());
    }

    @Test
    public void testItemsNull() {
        donation.setId(1L);
        donation.setDate(new Date());
        donation.setCenter(new CenterEntity());
        donation.setItems(null);
        assertFalse(violations.getConstraintViolations(donation).isEmpty());
    }
}
