package br.com.meli.dhprojetointegrador.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "seller")
public class Seller {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;

  @NotBlank(message = "O campo nome não pode estar em branco")
  @NotNull(message = "O campo nome não pode ser nulo")
  private String name;

  @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
  private Set<Product> products;
}
