package com.compass.order;

import com.compass.item.entities.ItemEntity;
import com.compass.order.enums.StatusOrder;
import com.compass.shelter.ShelterEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Status do pedido é obrigatório")
    private StatusOrder status;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    @NotNull(message = "Abrigo do pedido é obrigatório")
    private ShelterEntity shelter;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ordem_id")
    @NotEmpty(message = "A lista de itens do pedido não pode ser vazia ou nula")
    private List<ItemEntity> items;
}
