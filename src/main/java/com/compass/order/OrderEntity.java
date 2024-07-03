package com.compass.order;

import com.compass.item.ItemEntity;
import com.compass.order.enums.StatusOrder;
import com.compass.shelter.ShelterEntity;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TB_ORDERS")
public class OrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOrder status;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private ShelterEntity shelter;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ordem_id")
    private List<ItemEntity> items;

    public OrderEntity() {}

    public OrderEntity(Date date, StatusOrder status) {
        this.date = date;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public StatusOrder getStatus() {
        return status;
    }

    public void setStatus(StatusOrder status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}
