package br.com.meli.dhprojetointegrador.entity;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;
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

  @ApiModelProperty(value = "Código do seller")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  
  @NotNull(message = "O campo name não pode ser nulo")
  private String name;

  @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
  private Set<Product> products;
}
