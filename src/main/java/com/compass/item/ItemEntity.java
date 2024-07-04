package com.compass.item;

import com.compass.center.CenterEntity;
import com.compass.item.enums.SexItem;
import com.compass.item.enums.SizeItem;
import com.compass.item.enums.TypeItem;
import com.compass.shelter.ShelterEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "TB_ITEMS")
@Data
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

    @ManyToOne
    @JoinColumn(name = "center_id")
    private CenterEntity center;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private ShelterEntity shelter;
}
