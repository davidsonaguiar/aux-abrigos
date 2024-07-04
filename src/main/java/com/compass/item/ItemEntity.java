package com.compass.item;

import com.compass.center.CenterEntity;
import com.compass.item.enums.SexItem;
import com.compass.item.enums.SizeItem;
import com.compass.item.enums.TypeItem;
import com.compass.shelter.ShelterEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "TB_ITEMS")
@Data
public class ItemEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Nome do item é obrigatório")
    @Size(min = 3, max = 100, message = "Nome do item deve ter entre 3 e 100 caracteres")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Tipo do item é obrigatório (Roupa, Alimento ou Higiene)")
    private TypeItem type;

    @Enumerated(EnumType.STRING)
    @Pattern(regexp = "^(Infantil|PP|P|M|G|GG)$", message = "Tamanho do item deve ser Infantil, PP, P, M, G ou GG")
    private SizeItem size;

    @Enumerated(EnumType.STRING)
    @Pattern(regexp = "^(Masculino|Feminino|Unissex)$", message = "Sexo do item deve ser Masculino, Feminino ou Unissex")
    private SexItem sex;

    @Column(nullable = false)
    @NotNull(message = "Quantidade do item é obrigatória")
    @Min(value = 0, message = "Quantidade do item deve ser maior ou igual a 0")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private CenterEntity center;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private ShelterEntity shelter;
}
