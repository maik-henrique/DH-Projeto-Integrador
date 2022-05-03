package br.com.meli.dhprojetointegrador.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "section")
public class Section {

    @ApiModelProperty(value = "Código da section")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O campo nome não pode ser nulo")
    private String name;

    @NotNull(message = "O campo nome não pode ser nulo")
    @NumberFormat
    private Float capacity;

    @ApiModelProperty(value = "Código da warehouse")
    @ManyToOne
    @JoinColumn(name = "warehouse", nullable = false)
    private Warehouse warehouse;

    @ApiModelProperty(value = "Código da category")
    @ManyToOne
    @JoinColumn(name = "category", nullable = false)
    private Category category;

}
