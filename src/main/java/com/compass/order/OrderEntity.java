package com.compass.order;

import com.compass.center.CenterEntity;
import com.compass.item.ItemEntity;
import com.compass.item.enums.*;
import com.compass.order.enums.StatusOrder;
import com.compass.shelter.ShelterEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "TB_ORDERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    @NotNull(message = "Data do pedido é obrigatória")
    @PastOrPresent(message = "Data do pedido deve ser no passado ou presente")
    private LocalDate date;

    @NotNull
    @Min(value = 1, message = "O valor mínimo do pedido é 1")
    private Integer quantity;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CategoryItem categoryItem;

    @Enumerated(EnumType.STRING)
    private SizeItem sizeItem;

    @Enumerated(EnumType.STRING)
    private GenderItem genderItem;

    @Enumerated(EnumType.STRING)
    private HygieneTypeItem hygieneType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Status do pedido é obrigatório")
    private StatusOrder status;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    @NotNull(message = "Abrigo do pedido é obrigatório")
    private ShelterEntity shelter;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private CenterEntity supplierCenter;

    @ManyToMany
    @JoinTable(
            name = "order_center",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "center_id")
    )
    private List<CenterEntity> centersRequested;
}
