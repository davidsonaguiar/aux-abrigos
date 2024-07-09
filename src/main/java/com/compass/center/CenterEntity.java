package com.compass.center;

import com.compass.item.entities.ItemEntity;
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
}
