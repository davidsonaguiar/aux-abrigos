package com.compass.item;

import com.compass.center.CenterEntity;
import com.compass.item.enums.SexItem;
import com.compass.item.enums.SizeItem;
import com.compass.item.enums.TypeItem;
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
        item = new ItemEntity();
        violations = new UtilConstraintViolation<>();
    }

    @Test
    public void testValid() {
        item.setId(1L);
        item.setName("Camiseta");
        item.setType(TypeItem.ROUPA);
        item.setSize(SizeItem.P);
        item.setSex(SexItem.MASCULINO);
        item.setQuantity(10);
        item.setCenter(new CenterEntity());
        assertTrue(violations.getConstraintViolations(item).isEmpty());
    }

    @Test
    public void testInvalidNameEmpty() {
        item.setId(1L);
        item.setName("");
        item.setType(TypeItem.ALIMENTO);
        item.setQuantity(10);
        item.setCenter(new CenterEntity());
        assertFalse(violations.getConstraintViolations(item).isEmpty());
    }

    @Test
    public void testInvalidNameMin() {
        item.setId(1L);
        item.setName("A");
        item.setType(TypeItem.ALIMENTO);
        item.setQuantity(10);
        item.setCenter(new CenterEntity());
        assertTrue(violations.getConstraintViolations(item).size() == 1);
    }

    @Test
    public void testInvalidNameMax() {
        item.setId(1L);
        item.setName("A".repeat(101));
        item.setType(TypeItem.ALIMENTO);
        item.setQuantity(10);
        item.setCenter(new CenterEntity());
        assertTrue(violations.getConstraintViolations(item).size() == 1);
    }

    @Test
    public void testInvalidNameNull() {
        item.setId(1L);
        item.setName(null);
        item.setType(TypeItem.ALIMENTO);
        item.setQuantity(10);
        item.setCenter(new CenterEntity());
        assertTrue(violations.getConstraintViolations(item).size() == 1);
    }

    @Test
    public void testValidWithoutSizeAndSex() {
        item.setId(1L);
        item.setName("Arroz");
        item.setType(TypeItem.ALIMENTO);
        item.setQuantity(10);
        item.setCenter(new CenterEntity());
        assertTrue(violations.getConstraintViolations(item).isEmpty());
    }

    @Test
    public void testInvalidTypeItemNull() {
        item.setId(1L);
        item.setName("Arroz");
        item.setQuantity(10);
        item.setCenter(new CenterEntity());
        item.setType(null);
        assertFalse(violations.getConstraintViolations(item).isEmpty());
    }

    @Test
    public void testInvalidCenterNull() {
        item.setId(1L);
        item.setName("Arroz");
        item.setType(TypeItem.ALIMENTO);
        item.setQuantity(10);
        assertFalse(violations.getConstraintViolations(item).isEmpty());
    }

    @Test
    public void testInvalidQuantityMin() {
        item.setId(1L);
        item.setName("Arroz");
        item.setType(TypeItem.ALIMENTO);
        item.setQuantity(-1);
        item.setCenter(new CenterEntity());
        assertFalse(violations.getConstraintViolations(item).isEmpty());
    }

    @Test
    public void testInvalidQuantityNull() {
        item.setId(1L);
        item.setName("Arroz");
        item.setType(TypeItem.ALIMENTO);
        item.setCenter(new CenterEntity());
        assertFalse(violations.getConstraintViolations(item).isEmpty());
    }

    @Test
    public void testInvalidQuantityZero() {
        item.setId(1L);
        item.setName("Arroz");
        item.setType(TypeItem.ALIMENTO);
        item.setQuantity(0);
        item.setCenter(new CenterEntity());
        assertTrue(violations.getConstraintViolations(item).isEmpty());
    }
}
