package com.compass.donation;

import com.compass.center.CenterEntity;
import com.compass.item.ItemEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TB_DONATIONS")
@Data
public class DonationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "center_id", nullable = false)
    private CenterEntity center;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "donation_id")
    private List<ItemEntity> items;
}
