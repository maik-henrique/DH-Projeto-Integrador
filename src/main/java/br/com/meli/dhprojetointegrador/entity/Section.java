package br.com.meli.dhprojetointegrador.entity;

import lombok.*;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "section")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O campo nome não pode ser nulo")
    
    private String name;

    @NotNull(message = "O campo nome não pode ser nulo")
    @NumberFormat
    private Float capacity;

    @ManyToOne
    @JoinColumn(name = "warehouse", nullable = false)
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "category", nullable = false)
    private Category category;

}
