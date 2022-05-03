package br.com.meli.dhprojetointegrador.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
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
@Entity(name = "warehouse")
public class Warehouse {

  @ApiModelProperty(value = "Código da Werehouse")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  
  @NotNull(message = "O campo name não pode ser nulo")
  private String name;

  @ApiModelProperty(value = "Código do agent")
  @OneToOne(mappedBy = "warehouse", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private Agent agent;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "warehouse")
  private List<Section> sections;
}
