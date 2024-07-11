package com.compass.order;

import com.compass.item.ItemEntity;
import com.compass.order.enums.StatusOrder;
import com.compass.shelter.ShelterEntity;

import com.compass.util.UtilConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderEntityTest {
    private OrderEntity orderEntity;
    private UtilConstraintViolation<OrderEntity> validation;
    private Date date;

    @BeforeEach
    public void init() {
        date = new Date();
        orderEntity = new OrderEntity(1L, date, StatusOrder.PEDENTE, new ShelterEntity(), List.of(new ItemEntity()));
        validation = new UtilConstraintViolation<>();
    }

    // Test Getter and Setter
    @Test
    public void testGetters() {
        assertEquals(1L, orderEntity.getId());
        assertEquals(date, orderEntity.getDate());
        assertEquals(StatusOrder.PEDENTE, orderEntity.getStatus());
        assertEquals(new ShelterEntity(), orderEntity.getShelter());
        assertEquals(List.of(new ItemEntity()), orderEntity.getItems());
    }

    @Test
    public void testSetters() {
        orderEntity.setId(2L);
        orderEntity.setDate(new Date());
        orderEntity.setStatus(StatusOrder.ACEITO);
        orderEntity.setShelter(new ShelterEntity());
        orderEntity.setItems(List.of(new ItemEntity()));

        assertEquals(2L, orderEntity.getId());
        assertTrue(orderEntity.getDate() instanceof Date);
        assertEquals(StatusOrder.ACEITO, orderEntity.getStatus());
        assertEquals(new ShelterEntity(), orderEntity.getShelter());
        assertEquals(List.of(new ItemEntity()), orderEntity.getItems());
    }

    // Test ID
    @Test
    public void testIdNull() {
        orderEntity.setId(null);
        assertTrue(validation.getConstraintViolations(orderEntity).isEmpty());
    }

    // Test Date
    @Test
    public void testDateNull() {
        orderEntity.setDate(null);
        assertFalse(validation.getConstraintViolations(orderEntity).isEmpty());
    }

    @Test
    public void testDateFuture() {
        orderEntity.setDate(new Date(System.currentTimeMillis() + 1000));
        assertFalse(validation.getConstraintViolations(orderEntity).isEmpty());
    }

    @Test
    public void testDatePast() {
        orderEntity.setDate(new Date(System.currentTimeMillis() - 1000));
        assertTrue(validation.getConstraintViolations(orderEntity).isEmpty());
    }

    // Test Status
    @Test
    public void testStatusNull() {
        orderEntity.setStatus(null);
        assertFalse(validation.getConstraintViolations(orderEntity).isEmpty());
    }

    // Test Shelter
    @Test
    public void testShelterNull() {
        orderEntity.setShelter(null);
        assertFalse(validation.getConstraintViolations(orderEntity).isEmpty());
    }

    // Test Items
    @Test
    public void testItemsNull() {
        orderEntity.setItems(null);
        assertFalse(validation.getConstraintViolations(orderEntity).isEmpty());
    }

    @Test
    public void testItemsEmpty() {
        orderEntity.setItems(List.of());
        assertFalse(validation.getConstraintViolations(orderEntity).isEmpty());
    }

    @Test
    public void testItemsNotEmpty() {
        orderEntity.setItems(List.of(new ItemEntity()));
        assertTrue(validation.getConstraintViolations(orderEntity).isEmpty());
    }
}
