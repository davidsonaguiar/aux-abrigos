package com.compass.shelter;

import com.compass.item.ItemEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TB_SHELTERS")
@Data
public class ShelterEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String responsible;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private Integer occupancy;

    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL)
    private List<ItemEntity> items;
}
