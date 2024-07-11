package com.compass.center;

import com.compass.item.ItemEntity;
import com.compass.util.UtilConstraintViolation;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CenterEntityTest {
    private CenterEntity center;
    private UtilConstraintViolation<CenterEntity> violations;

    @BeforeEach
    public void setUp() {
        center = new CenterEntity(1L, "Centro 1", "Rua 1, 123", 100, List.of(new ItemEntity()));
        violations = new UtilConstraintViolation<>();
    }

    // Test Getters and Setters
    @Test
    public void testGetters() {
        Assertions.assertEquals(1L, center.getId());
        Assertions.assertEquals("Centro 1", center.getName());
        Assertions.assertEquals("Rua 1, 123", center.getAddress());
        Assertions.assertEquals(100, center.getCapacity());
        Assertions.assertEquals(List.of(new ItemEntity()), center.getItems());
    }

    @Test
    public void testSetters() {
        center.setId(2L);
        center.setName("Centro 2");
        center.setAddress("Rua 2, 123");
        center.setCapacity(200);
        center.setItems(List.of(new ItemEntity(), new ItemEntity()));

        Assertions.assertEquals(2L, center.getId());
        Assertions.assertEquals("Centro 2", center.getName());
        Assertions.assertEquals("Rua 2, 123", center.getAddress());
        Assertions.assertEquals(200, center.getCapacity());
        Assertions.assertTrue(center.getItems().size() == 2);
    }


    // Test Name
    @Test
    public void testNameEmpty() {
        center.setName("");
        assertFalse(violations.getConstraintViolations(center).isEmpty());
    }

    @Test
    public void testNameNull() {
        center.setName(null);
        assertFalse(violations.getConstraintViolations(center).isEmpty());
    }

    @Test
    public void testNameMin() {
        center.setName("C");
        assertFalse(violations.getConstraintViolations(center).isEmpty());
    }

    @Test
    public void testNameMax() {
        center.setName("C".repeat(101));
        assertFalse(violations.getConstraintViolations(center).isEmpty());
    }

    // Test Address

    @Test
    public void testAddressEmpty() {
        center.setAddress("");
        assertFalse(violations.getConstraintViolations(center).isEmpty());
    }

    @Test
    public void testAddressNull() {
        center.setAddress(null);
        assertFalse(violations.getConstraintViolations(center).isEmpty());
    }

    @Test
    public void testAddressMin() {
        center.setAddress("R");
        assertFalse(violations.getConstraintViolations(center).isEmpty());
    }

    @Test
    public void testAddressMax() {
        center.setAddress("R".repeat(101));
        assertFalse(violations.getConstraintViolations(center).isEmpty());
    }

    // Test Capacity
    @Test
    public void testCapacityNull() {
        center.setCapacity(null);
        assertFalse(violations.getConstraintViolations(center).isEmpty());
    }

    @Test
    public void testCapacityMin() {
        center.setCapacity(99);
        assertFalse(violations.getConstraintViolations(center).isEmpty());
    }

    // Items
    @Test
    public void testItemsNull() {
        center.setItems(null);
        assertTrue(violations.getConstraintViolations(center).isEmpty());
    }

    @Test
    public void testItemsEmpty() {
        center.setItems(List.of());
        assertTrue(violations.getConstraintViolations(center).isEmpty());
    }

    @Test
    public void testItemsNotEmpty() {
        center.setItems(List.of(new ItemEntity()));
        assertTrue(violations.getConstraintViolations(center).isEmpty());
    }
}
