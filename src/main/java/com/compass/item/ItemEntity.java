package com.compass.item;

import com.compass.center.CenterEntity;
import com.compass.item.enums.*;
import com.compass.order.OrderEntity;
import com.compass.shelter.ShelterEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "TB_ITEMS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 3, max = 100, message = "Descrição do item deve ter entre 3 e 100 caracteres")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "Categoria do item é obrigatória (Roupa, Alimento ou Higiene)")
    @Enumerated(EnumType.STRING)
    private CategoryItem category;

    @ManyToOne
    @JoinColumn(name = "center_id", nullable = false)
    private CenterEntity center;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private ShelterEntity shelter;

    @Enumerated(EnumType.STRING)
    private SizeItem size;

    @Enumerated(EnumType.STRING)
    private GenderItem gender;

    @Min(value = 0, message = "Quantidade do item deve ser maior ou igual a 0")
    private Integer quantity;

    @Temporal(TemporalType.DATE)
    @Future(message = "Data de validade deve ser no presente ou futuro")
    private LocalDate expirationDate;

    @Enumerated(EnumType.STRING)
    private UnitItem unit;

    @Enumerated(EnumType.STRING)
    private HygieneTypeItem hygieneType;
}