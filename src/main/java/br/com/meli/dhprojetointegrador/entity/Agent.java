package br.com.meli.dhprojetointegrador.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.*;
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
@Entity(name = "agent")
public class Agent {

  @ApiModelProperty(value = "Código do agent")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  
  @NotNull(message = "O campo name não pode ser nulo")
  private String name;

  
  @NotNull(message = "O campo password não pode ser nulo")
  @Size(min = 6, max = 8, message = "Senha tem que ser de 6 a 8 caracteres!")
  private String password;

  @ApiModelProperty(value = "nome da warehouse")
  @OneToOne
  @MapsId
  @JoinColumn(name = "agent_id")
  private Warehouse warehouse;

}
