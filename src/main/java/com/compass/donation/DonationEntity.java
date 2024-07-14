package com.compass.donation;

import com.compass.center.CenterEntity;
import com.compass.item.ItemEntity;
import com.compass.item.dtos.ItemDto;
import com.compass.item.enums.CategoryItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "TB_DONATIONS")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DonationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    @NotNull(message = "Data da doação é obrigatória")
    @PastOrPresent(message = "Data da doação deve ser a atual")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "center_id", nullable = false)
    @NotNull(message = "Centro de doação é obrigatório")
    private CenterEntity center;

    @OneToMany
    @JoinColumn(name = "donation_id")
    @NotNull(message = "A lista de itens da doação não pode ser vazia ou nula")
    private List<ItemEntity> items;

    public void addItem(ItemEntity item) {
        items.add(item);
    }

    public Integer getQuantityItemsByCategory(CategoryItem category) {
        return items.stream()
                .filter(item -> item.getCategory().equals(category))
                .mapToInt(ItemEntity::getQuantity)
                .sum();
    }
}
