package com.compass.shelter;

import com.compass.util.UtilConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShelterEntityTest {
    private ShelterEntity shelterEntity;
    private UtilConstraintViolation<ShelterEntity> violation;

    @BeforeEach
    public void setUp() {
        violation = new UtilConstraintViolation<>();
        shelterEntity = new ShelterEntity(
                1L,
                "Abrigo dos Anjos",
                "Rua dos Anjos, 123",
                "11999998888",
                "meuemail@email.com",
                "Anjo da Guarda",
                100,
                50,
                List.of(new ItemEntity()));
    }

    // Test Getter and Setter
    @Test
    public void testGetters() {
        assertEquals(1L, shelterEntity.getId());
        assertEquals("Abrigo dos Anjos", shelterEntity.getName());
        assertEquals("Rua dos Anjos, 123", shelterEntity.getAddress());
        assertEquals("11999998888", shelterEntity.getPhone());
        assertEquals("meuemail@email.com", shelterEntity.getEmail());
        assertEquals("Anjo da Guarda", shelterEntity.getResponsible());
        assertEquals(100, shelterEntity.getCapacity());
        assertEquals(50, shelterEntity.getOccupancy());
        assertEquals(1, shelterEntity.getItems().size());
    }

    @Test
    public void testSetters() {
        shelterEntity.setId(2L);
        shelterEntity.setName("Abrigo dos Santos");
        shelterEntity.setAddress("Rua dos Santos, 123");
        shelterEntity.setPhone("11999997777");
        shelterEntity.setEmail("j0va@email.com");
        shelterEntity.setResponsible("Santo Expedito");
        shelterEntity.setCapacity(200);
        shelterEntity.setOccupancy(100);
        shelterEntity.setItems(List.of(new ItemEntity(), new ItemEntity()));

        assertEquals(2L, shelterEntity.getId());
        assertEquals("Abrigo dos Santos", shelterEntity.getName());
        assertEquals("Rua dos Santos, 123", shelterEntity.getAddress());
        assertEquals("11999997777", shelterEntity.getPhone());
        assertEquals("j0va@email.com", shelterEntity.getEmail());
        assertEquals("Santo Expedito", shelterEntity.getResponsible());
        assertEquals(200, shelterEntity.getCapacity());
        assertEquals(100, shelterEntity.getOccupancy());
        assertEquals(2, shelterEntity.getItems().size());
    }

    // Test ID
    @Test
    public void testIdNull() {
        shelterEntity.setId(null);
        assertTrue(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    // Test Name
    @Test
    public void testNameNull() {
        shelterEntity.setName(null);
        assertFalse(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    @Test
    public void testNameEmpty() {
        shelterEntity.setName("");
        assertFalse(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    @Test
    public void testNameMin() {
        shelterEntity.setName("A");
        assertFalse(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    @Test
    public void testNameMax() {
        shelterEntity.setName("A".repeat(100));
        assertTrue(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    @Test
    public void testNameMaxPlus() {
        shelterEntity.setName("A".repeat(101));
        assertFalse(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    // Test Address
    @Test
    public void testAddressNull() {
        shelterEntity.setAddress(null);
        assertFalse(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    @Test
    public void testAddressEmpty() {
        shelterEntity.setAddress("");
        assertFalse(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    @Test
    public void testAddressMin() {
        shelterEntity.setAddress("A");
        assertFalse(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    @Test
    public void testAddressMax() {
        shelterEntity.setAddress("A".repeat(100));
        assertTrue(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    @Test
    public void testAddressMaxPlus() {
        shelterEntity.setAddress("A".repeat(101));
        assertFalse(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    // Test Phone

    @Test
    public void testPhoneNull() {
        shelterEntity.setPhone(null);
        assertFalse(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    @Test
    public void testPhoneEmpty() {
        shelterEntity.setPhone("");
        assertFalse(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    @Test
    public void testPhoneMin() {
        shelterEntity.setPhone("1199999888");
        assertFalse(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    @Test
    public void testPhoneMax() {
        shelterEntity.setPhone("11999998888");
        assertTrue(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    @Test
    public void testPhoneMaxPlus() {
        shelterEntity.setPhone("119999988888");
        assertFalse(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    // Test Email
    @Test
    public void testEmailNull() {
        shelterEntity.setEmail(null);
        assertFalse(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    @Test
    public void testEmailInvalid() {
        shelterEntity.setEmail("email.com");
        assertFalse(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    // Test Responsible
    @Test
    public void testResponsibleNull() {
        shelterEntity.setResponsible(null);
        assertFalse(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    @Test
    public void testResponsibleEmpty() {
        shelterEntity.setResponsible("");
        assertFalse(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    @Test
    public void testResponsibleMin() {
        shelterEntity.setResponsible("A");
        assertFalse(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    @Test
    public void testResponsibleMax() {
        shelterEntity.setResponsible("A".repeat(100));
        assertTrue(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    @Test
    public void testResponsibleMaxPlus() {
        shelterEntity.setResponsible("A".repeat(101));
        assertFalse(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    // Test Capacity
    @Test
    public void testCapacityNull() {
        shelterEntity.setCapacity(null);
        assertFalse(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    @Test
    public void testCapacityMin() {
        shelterEntity.setCapacity(0);
        assertFalse(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    // Test Occupancy
    @Test
    public void testOccupancyNull() {
        shelterEntity.setOccupancy(null);
        assertFalse(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    @Test
    public void testOccupancyMin() {
        shelterEntity.setOccupancy(-1);
        assertFalse(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    // Test Items
    @Test
    public void testItemsNull() {
        shelterEntity.setItems(null);
        assertTrue(violation.getConstraintViolations(shelterEntity).isEmpty());
    }

    @Test
    public void testItemsEmpty() {
        shelterEntity.setItems(List.of());
        assertTrue(violation.getConstraintViolations(shelterEntity).isEmpty());
    }
}
