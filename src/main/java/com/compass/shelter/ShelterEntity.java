package com.compass.shelter;

import com.compass.item.ItemEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "TB_SHELTERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShelterEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Nome do abrigo é obrigatório")
    @Size(min = 3, max = 100, message = "Nome do abrigo deve ter entre 3 e 100 caracteres")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Endereço do abrigo é obrigatório")
    @Size(min = 3, max = 100, message = "Endereço do abrigo deve ter entre 3 e 100 caracteres")
    private String address;

    @Column(nullable = false)
    @NotBlank(message = "Telefone do abrigo é obrigatório")
    @Size(min = 11, max = 11, message = "Telefone do abrigo deve ter 11 caracteres")
    private String phone;

    @Column(nullable = false)
    @NotBlank(message = "E-mail do abrigo é obrigatório")
    @Email(message = "E-mail do abrigo inválido")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Responsável do abrigo é obrigatório")
    @Size(min = 3, max = 100, message = "Responsável do abrigo deve ter entre 3 e 100 caracteres")
    private String responsible;

    @Column(nullable = false)
    @NotNull(message = "Capacidade de itens do abrigo é obrigatória")
    @Min(value = 1, message = "Capacidade de itens do abrigo deve ser maior que 0")
    private Integer capacityItem;

    @Column(nullable = false)
    @NotNull(message = "Capacidade de pessoas do abrigo é obrigatória")
    @Min(value = 1, message = "Capacidade de pessoas do abrigo deve ser maior que 0")
    private Integer capacityPeople;

    @Column(nullable = false)
    @NotNull(message = "Ocupação do abrigo é obrigatória")
    @Min(value = 0, message = "Ocupação do abrigo deve ser maior ou igual a 0")
    private Integer occupancy;

    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL)
    private List<ItemEntity> items;
}
