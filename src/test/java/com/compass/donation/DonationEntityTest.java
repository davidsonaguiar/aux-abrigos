package com.compass.donation;

import com.compass.center.CenterEntity;
import com.compass.item.ItemEntity;
import com.compass.util.UtilConstraintViolation;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class DonationEntityTest {
    private DonationEntity donation;
    private UtilConstraintViolation<DonationEntity> violations;
    private LocalDate date = LocalDate.now();

    @BeforeEach
    public void setUp() {
        donation = new DonationEntity(1L, date, new CenterEntity(), List.of(new ItemEntity()));
        violations = new UtilConstraintViolation<>();
    }

    // Test Getters and Setter
    @Test
    public void testGetters() {
        assertTrue(donation.getId() == 1L);
        assertTrue(donation.getDate().equals(date));
        assertTrue(donation.getCenter().equals(new CenterEntity()));
        assertTrue(donation.getItems().equals(List.of(new ItemEntity())));
    }

    @Test
    public void testSetter() {
        donation.setId(2L);
        donation.setDate(LocalDate.now().minusDays(1));
        donation.setCenter(new CenterEntity());
        donation.setItems(List.of(new ItemEntity()));

        assertTrue(donation.getId() == 2L);
        assertTrue(donation.getDate().equals(LocalDate.now().minusDays(1)));
        assertTrue(donation.getCenter().equals(new CenterEntity()));
        assertTrue(donation.getItems().equals(List.of(new ItemEntity())));
    }

    // Test Date

    @Test
    public void testDateNull() {
        donation.setDate(null);
        assertFalse(violations.getConstraintViolations(donation).isEmpty());
    }

    @Test
    public void testDateFuture() {
        donation.setDate(LocalDate.now().plusDays(1));
        assertFalse(violations.getConstraintViolations(donation).isEmpty());
    }

    @Test
    public void testDatePast() {
        donation.setDate(LocalDate.now().minusDays(1));
        assertTrue(violations.getConstraintViolations(donation).isEmpty());
    }

    // Test Center
    @Test
    public void testCenterNull() {
        donation.setCenter(null);
        assertFalse(violations.getConstraintViolations(donation).isEmpty());
    }

    // Test Items
    @Test
    public void testItemsEmpty() {
        donation.setItems(List.of());
        assertFalse(violations.getConstraintViolations(donation).isEmpty());
    }

    @Test
    public void testItemsNull() {
        donation.setItems(null);
        assertFalse(violations.getConstraintViolations(donation).isEmpty());
    }

    @Test
    public void testItems() {
        donation.setItems(List.of(new ItemEntity(), new ItemEntity()));
        assertTrue(violations.getConstraintViolations(donation).isEmpty());
    }
}
