package br.com.meli.dhprojetointegrador.entity;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.*;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long id;

    @NotBlank(message = "O campo nome não pode estar em branco")
    @NotNull(message = "O campo nome não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private CategoryEnum name;

    @NotBlank(message = "O campo nome não pode estar em branco")
    @NotNull(message = "O campo nome não pode ser nulo")
    @NotEmpty(message = "Temperatura contra indicada!")
    @Size(min = -22, max = 0, message = "Risco de temperatura!")
    private float minimumTemperature;

    @NotBlank(message = "O campo nome não pode estar em branco")
    @NotNull(message = "O campo nome não pode ser nulo")
    @NotEmpty(message = "Temperatura contra indicada!")
    @Size(min = -22, max = 0, message = "Risco de temperatura!")
    private float maximumTemperature;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private Set<Product> products;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "category")
    private Set<Section> sections;
}
