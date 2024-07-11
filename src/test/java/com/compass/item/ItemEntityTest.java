package com.compass.item;

import com.compass.center.CenterEntity;
import com.compass.item.enums.GenderItem;
import com.compass.item.enums.SizeItem;
import com.compass.item.enums.CategoryItem;
import com.compass.item.enums.Unit;
import com.compass.shelter.ShelterEntity;
import com.compass.util.UtilConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ItemEntityTest {
    private ItemEntity item;

    private UtilConstraintViolation<ItemEntity> violations;

    @BeforeEach
    public void setUp() {
        item = new ItemEntity();
        item.setId(1L);
        item.setDescription("Arroz");
        item.setCategory(CategoryItem.ALIMENTO);
        item.setQuantity(15);
        item.setUnit(Unit.QUILOGRAMA);
        item.setCenter(new CenterEntity());

        violations = new UtilConstraintViolation<>();
    }

    // Test Getters e Setters
    @Test
    public void testGetters() {
        item.setSize(SizeItem.P);
        item.setGender(GenderItem.MASCULINO);

        assertTrue(item.getId() == 1L);
        assertTrue(item.getDescription().equals("Arroz"));
        assertTrue(item.getCategory().equals(CategoryItem.ALIMENTO));
        assertTrue(item.getSize().equals(SizeItem.P));
        assertTrue(item.getGender().equals(GenderItem.MASCULINO));
        assertTrue(item.getQuantity() == 15);
        assertTrue(item.getCenter().equals(new CenterEntity()));
    }

    @Test
    public void testSetters() {
        item.setId(2L);
        item.setDescription("Feijão");
        item.setCategory(CategoryItem.ALIMENTO);
        item.setSize(SizeItem.M);
        item.setGender(GenderItem.FEMININO);
        item.setQuantity(20);
        item.setCenter(new CenterEntity());
        item.setShelter(new ShelterEntity());

        assertTrue(item.getId() == 2L);
        assertTrue(item.getDescription().equals("Feijão"));
        assertTrue(item.getCategory().equals(CategoryItem.ALIMENTO));
        assertTrue(item.getSize().equals(SizeItem.M));
        assertTrue(item.getGender().equals(GenderItem.FEMININO));
        assertTrue(item.getQuantity() == 20);
        assertTrue(item.getCenter().equals(new CenterEntity()));
        assertTrue(item.getShelter().equals(new ShelterEntity()));
    }

    // Test Name
    @Test
    public void testNameEmpty() {
        item.setDescription("");
        assertFalse(violations.getConstraintViolations(item).isEmpty());
    }

    @Test
    public void testNameMin() {
        item.setDescription("A");
        assertTrue(violations.getConstraintViolations(item).size() == 1);
    }

    @Test
    public void testNameMax() {
        item.setDescription("A".repeat(101));
        assertTrue(violations.getConstraintViolations(item).size() == 1);
    }

    @Test
    public void testNameNull() {
        item.setDescription(null);
        assertTrue(violations.getConstraintViolations(item).size() == 1);
    }

    // Test Type
    @Test
    public void testWithoutSizeAndSex() {
        item.setSize(null);
        item.setGender(null);
        assertTrue(violations.getConstraintViolations(item).isEmpty());
    }

    @Test
    public void testTypeItemNull() {
        item.setCategory(null);
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
