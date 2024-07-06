package com.compass.donation;

import com.compass.center.CenterEntity;
import com.compass.item.ItemEntity;
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
import java.util.Objects;

@Entity
@Table(name = "TB_DONATIONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    @NotNull(message = "Data da doação é obrigatória")
    @PastOrPresent(message = "Data da doação deve ser a atual")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "center_id", nullable = false)
    @NotNull(message = "Centro de doação é obrigatório")
    private CenterEntity center;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "donation_id")
    @NotEmpty(message = "A lista de itens da doação não pode ser vazia ou nula")
    private List<ItemEntity> items;
}
