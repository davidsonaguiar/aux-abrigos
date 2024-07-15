package com.compass.order_center;

import com.compass.center.CenterEntity;
import com.compass.order.OrderEntity;
import com.compass.order.enums.StatusOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_center")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCenterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private CenterEntity center;

    @Enumerated(EnumType.STRING)
    private StatusOrder status;
}