package br.com.meli.dhprojetointegrador.entity;

import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"name"}))
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CategoryEnum name;
    private float minimumTemperature;
    private float maximumTemperature;

    @OneToMany
    private List<Product> categories;
}
