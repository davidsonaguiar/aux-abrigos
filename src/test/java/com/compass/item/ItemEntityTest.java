package com.compass.item;

import com.compass.center.CenterEntity;
import com.compass.item.enums.GenderItem;
import com.compass.item.enums.SizeItem;
import com.compass.item.enums.CategoryItem;
import com.compass.shelter.ShelterEntity;
import com.compass.util.UtilConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ItemEntityTest {
    private ItemEntity item;
    private UtilConstraintViolation<ItemEntity> violations;

    @BeforeEach
    public void setUp() {
        item = new ItemEntity(1L, "Arroz", CategoryItem.ALIMENTO, SizeItem.P, GenderItem.MASCULINO, 10, new CenterEntity(), new ShelterEntity());
        violations = new UtilConstraintViolation<>();
    }

    // Test Getters e Setters
    @Test
    public void testGetters() {
        assertTrue(item.getId() == 1L);
        assertTrue(item.getName().equals("Arroz"));
        assertTrue(item.getType().equals(CategoryItem.ALIMENTO));
        assertTrue(item.getSize().equals(SizeItem.P));
        assertTrue(item.getSex().equals(GenderItem.MASCULINO));
        assertTrue(item.getQuantity() == 10);
        assertTrue(item.getCenter().equals(new CenterEntity()));
        assertTrue(item.getShelter().equals(new ShelterEntity()));
    }

    @Test
    public void testSetters() {
        item.setId(2L);
        item.setName("Feijão");
        item.setType(CategoryItem.ALIMENTO);
        item.setSize(SizeItem.M);
        item.setSex(GenderItem.FEMININO);
        item.setQuantity(20);
        item.setCenter(new CenterEntity());
        item.setShelter(new ShelterEntity());

        assertTrue(item.getId() == 2L);
        assertTrue(item.getName().equals("Feijão"));
        assertTrue(item.getType().equals(CategoryItem.ALIMENTO));
        assertTrue(item.getSize().equals(SizeItem.M));
        assertTrue(item.getSex().equals(GenderItem.FEMININO));
        assertTrue(item.getQuantity() == 20);
        assertTrue(item.getCenter().equals(new CenterEntity()));
        assertTrue(item.getShelter().equals(new ShelterEntity()));
    }

    // Test Name
    @Test
    public void testNameEmpty() {
        item.setName("");
        assertFalse(violations.getConstraintViolations(item).isEmpty());
    }

    @Test
    public void testNameMin() {
        item.setName("A");
        assertTrue(violations.getConstraintViolations(item).size() == 1);
    }

    @Test
    public void testNameMax() {
        item.setName("A".repeat(101));
        assertTrue(violations.getConstraintViolations(item).size() == 1);
    }

    @Test
    public void testNameNull() {
        item.setName(null);
        assertTrue(violations.getConstraintViolations(item).size() == 1);
    }

    // Test Type
    @Test
    public void testWithoutSizeAndSex() {
        item.setSize(null);
        item.setSex(null);
        assertTrue(violations.getConstraintViolations(item).isEmpty());
    }

    @Test
    public void testTypeItemNull() {
        item.setType(null);
        assertFalse(violations.getConstraintViolations(item).isEmpty());
    }

    // Test Quantity
    @Test
    public void testQuantityMin() {
        item.setQuantity(-1);
        assertFalse(violations.getConstraintViolations(item).isEmpty());
    }

    @Test
    public void testQuantityNull() {
        item.setQuantity(null);
        assertFalse(violations.getConstraintViolations(item).isEmpty());
    }

    @Test
    public void testQuantityZero() {
        item.setQuantity(0);
        assertTrue(violations.getConstraintViolations(item).isEmpty());
    }

    // Test Shelter
    @Test
    public void testShelterNull() {
        item.setShelter(null);
        assertTrue(violations.getConstraintViolations(item).isEmpty());
    }

    // Test Center
    @Test
    public void testCenterNull() {
        item.setCenter(null);
        assertFalse(violations.getConstraintViolations(item).isEmpty());
    }
}
