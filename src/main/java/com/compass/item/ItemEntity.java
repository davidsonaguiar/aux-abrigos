package com.compass.item;

import com.compass.center.CenterEntity;
import com.compass.item.enums.*;
import com.compass.shelter.ShelterEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "TB_ITEMS")
@Data
@NoArgsConstructor
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Descrição do item é obrigatória")
    @Size(min = 3, max = 100, message = "Descrição do item deve ter entre 3 e 100 caracteres")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "Quantidade do item é obrigatória")
    @Min(value = 0, message = "Quantidade do item deve ser maior ou igual a 0")
    private Integer quantity;

    @Column(nullable = false)
    @NotNull(message = "Categoria do item é obrigatória (Roupa, Alimento ou Higiene)")
    @Enumerated(EnumType.STRING)
    private CategoryItem category;

    @ManyToOne
    @JoinColumn(name = "center_id", nullable = false)
    @NotNull(message = "Centro para qual o item será doado é obrigatório")
    private CenterEntity center;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private ShelterEntity shelter;

    @Enumerated(EnumType.STRING)
    private SizeItem size;

    @Enumerated(EnumType.STRING)
    private GenderItem gender;

    @Temporal(TemporalType.DATE)
    @Future(message = "Data de validade deve ser no presente ou futuro")
    private LocalDate expirationDate;

    @Enumerated(EnumType.STRING)
    private UnitItem unit;

    @Enumerated(EnumType.STRING)
    private HygieneTypeItem hygieneType;

    public ItemEntity(Long id, String description, Integer quantity, CategoryItem category, CenterEntity center) {
        this.id = id;
        this.description = description;
        this.quantity = quantity;
    }
}