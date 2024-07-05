package com.compass.center;

import com.compass.util.UtilConstraintViolation;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CenterEntityTest {
    private CenterEntity center;
    private UtilConstraintViolation<CenterEntity> violations;

    @BeforeEach
    public void setUp() {
        center = new CenterEntity();
        violations = new UtilConstraintViolation<>();
    }


    @Test
    public void testEmptyName() {
        center.setName("");
        center.setId(1L);
        center.setAddress("Rua 1, 123");
        center.setCapacity(100);
        assertFalse(violations.getConstraintViolations(center).isEmpty());
    }

    @Test
    public void testEmptyAddress() {
        center.setName("Centro 1");
        center.setId(1L);
        center.setAddress("");
        center.setCapacity(100);
        assertFalse(violations.getConstraintViolations(center).isEmpty());
    }

    @Test
    public void testInvalidCapacity() {
        center.setName("Centro 1");
        center.setId(1L);
        center.setAddress("Rua 1, 123");
        center.setCapacity(50);
        assertFalse(violations.getConstraintViolations(center).isEmpty());
    }

    @Test
    public void testNullCapacity() {
        center.setName("Centro 1");
        center.setId(1L);
        center.setAddress("Rua 1, 123");
        center.setCapacity(null);
        assertFalse(violations.getConstraintViolations(center).isEmpty());
    }

    @Test
    public void testNullName() {
        center.setName(null);
        center.setId(1L);
        center.setAddress("Rua 1, 123");
        center.setCapacity(100);
        assertFalse(violations.getConstraintViolations(center).isEmpty());
    }

    @Test
    public void testNullAddress() {
        center.setName("Centro 1");
        center.setId(1L);
        center.setAddress(null);
        center.setCapacity(100);
        assertFalse(violations.getConstraintViolations(center).isEmpty());
    }

    @Test
    public void testValidCenter() {
        center.setName("Centro 1");
        center.setId(1L);
        center.setAddress("Rua 1, 123");
        center.setCapacity(100);
        assertTrue(violations.getConstraintViolations(center).isEmpty());
    }

    @Test
    public void testInvalidName() {
        center.setName("C");
        center.setId(1L);
        center.setAddress("Rua 1, 123");
        center.setCapacity(100);
        assertFalse(violations.getConstraintViolations(center).isEmpty());
    }

    @Test
    public void testInvalidAddress() {
        center.setName("Centro 1");
        center.setId(1L);
        center.setAddress("R");
        center.setCapacity(100);
        assertFalse(violations.getConstraintViolations(center).isEmpty());
    }

    @Test
    public void testInvalidNameMinCharacters() {
        center.setName("C");
        center.setId(1L);
        center.setAddress("Rua 1, 123");
        center.setCapacity(100);
        assertFalse(violations.getConstraintViolations(center).isEmpty());
    }

    @Test
    public void testInvalidNameMaxCharacters() {
        center.setName("C".repeat(101));
        center.setId(1L);
        center.setAddress("Rua 1, 123");
        center.setCapacity(100);
        assertFalse(violations.getConstraintViolations(center).isEmpty());
    }

    @Test
    public void testInvalidAddressMinCharacters() {
        center.setName("Centro 1");
        center.setId(1L);
        center.setAddress("R");
        center.setCapacity(100);
        assertFalse(violations.getConstraintViolations(center).isEmpty());
    }

    @Test
    public void testInvalidAddressMaxCharacters() {
        center.setName("Centro 1");
        center.setId(1L);
        center.setAddress("R".repeat(101));
        center.setCapacity(100);
        assertFalse(violations.getConstraintViolations(center).isEmpty());
    }
}
