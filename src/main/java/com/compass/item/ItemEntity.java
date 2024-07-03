package com.compass.item;

import com.compass.item.enums.SexItem;
import com.compass.item.enums.SizeItem;
import com.compass.item.enums.TypeItem;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "TB_ITEMS")
public class ItemEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeItem type;

    @Enumerated(EnumType.STRING)
    private SizeItem size;

    @Enumerated(EnumType.STRING)
    private SexItem sex;

    @Column(nullable = false)
    private Integer quantity;

    public ItemEntity() {}

    public ItemEntity(Long id, String name, TypeItem type, SizeItem size, Integer quantity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.size = size;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeItem getType() {
        return type;
    }

    public void setType(TypeItem type) {
        this.type = type;
    }

    public SizeItem getSize() {
        return size;
    }

    public void setSize(SizeItem size) {
        this.size = size;
    }

    public SexItem getSex() {
        return sex;
    }

    public void setSex(SexItem sex) {
        this.sex = sex;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemEntity that = (ItemEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ItemEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", size=" + size +
                ", quantity=" + quantity +
                '}';
    }
}
