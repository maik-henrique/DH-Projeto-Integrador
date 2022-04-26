package br.com.meli.dhprojetointegrador.entity;

import java.util.Set;

import javax.persistence.*;

import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private CategoryEnum name;
    private float minimumTemperature;
    private float maximumTemperature;

    @OneToMany(mappedBy = "category")
    private Set<Product> products;

    @OneToMany(cascade = CascadeType.MERGE)
    private Set<Section> sections;
}
