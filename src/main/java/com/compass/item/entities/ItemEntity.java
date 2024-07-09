package com.compass.item.entities;

import com.compass.center.CenterEntity;
import com.compass.item.enums.CategoryItem;
import com.compass.shelter.ShelterEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_ITEMS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "category", discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
public abstract class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Descrição do item é obrigatória")
    @Size(min = 3, max = 100, message = "Descrição do item deve ter entre 3 e 100 caracteres")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "Quantidade do item é obrigatória")
    private Integer quantity;

    private transient CategoryItem category;

    @ManyToOne
    @JoinColumn(name = "center_id", nullable = false)
    @NotNull(message = "Centro para qual o item será doado é obrigatório")
    private CenterEntity center;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private ShelterEntity shelter;

    public ItemEntity(Long id, String description, Integer quantity) {
        this.id = id;
        this.description = description;
        this.quantity = quantity;
    }

    @PostLoad
    private void loadCategory() {
        this.category = CategoryItem.valueOf(this.getClass().getAnnotation(DiscriminatorValue.class).value());
    }
}