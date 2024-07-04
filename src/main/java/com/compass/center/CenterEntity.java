package com.compass.center;

import com.compass.item.ItemEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TB_CERTERS")
@Data
public class CenterEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Integer capacity;

    @OneToMany(mappedBy = "center", cascade = CascadeType.ALL)
    private List<ItemEntity> items;

    public CenterEntity() {}
}
