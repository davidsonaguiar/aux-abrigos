package com.compass.center;

import com.compass.common.exception.NotFoundException;
import com.compass.item.ItemEntity;
import com.compass.item.enums.CategoryItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "TB_CENTERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenterEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Nome do centro é obrigatório")
    @Size(min = 3, max = 100, message = "Nome do centro deve ter entre 3 e 100 caracteres")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Endereço do centro é obrigatório")
    @Size(min = 3, max = 100, message = "Endereço do centro deve ter entre 3 e 100 caracteres")
    private String address;

    @Column(nullable = false)
    @NotNull(message = "Capacidade do centro é obrigatória")
    @Min(value = 100, message = "Capacidade mínima do centro é 100")
    private Integer capacity;

    @OneToMany(mappedBy = "center", cascade = CascadeType.ALL)
    private List<ItemEntity> items;

    public boolean existsCapacityForCategoryItem(Integer quantity, CategoryItem categoryItem) {
        if(items.isEmpty())  return true;
        Integer quantityType = items.stream()
                .filter(item -> item.getCategory().equals(categoryItem) )
                .mapToInt(item -> item.getQuantity())
                .sum();
        return (capacity - quantityType) >= quantity;
    }

    public boolean existsCapacityForCategoryItem(CategoryItem categoryItem) {
        if(items.isEmpty())  return true;
        Integer quantityType = items.stream()
                .filter(item -> item.getCategory().equals(categoryItem) )
                .mapToInt(item -> item.getQuantity())
                .sum();
        return (capacity - quantityType) > 0;
    }

    public Integer getAvailableCapacityForCategory(CategoryItem categoryItem) {
        if(items.isEmpty())  return capacity;
        Integer quantityType = items.stream()
                .filter(item -> item.getCategory().equals(categoryItem) )
                .mapToInt(item -> item.getQuantity())
                .sum();
        return capacity - quantityType;
    }

    public List<CategoryItem> getCategoriesAvailableCapacity() {
        List<CategoryItem> categoryItems = List.of(CategoryItem.values());
        for(CategoryItem categoryItem : categoryItems) {
            if(!existsCapacityForCategoryItem(categoryItem)) {
                categoryItems.remove(categoryItem);
            }
        }
        return categoryItems;
    }
}
