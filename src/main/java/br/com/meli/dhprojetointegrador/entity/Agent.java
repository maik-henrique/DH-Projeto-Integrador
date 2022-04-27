package br.com.meli.dhprojetointegrador.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.*;
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

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "O campo nome não pode estar em branco")
  @NotNull(message = "O campo nome não pode ser nulo")
  private String name;

  @NotBlank(message = "O campo nome não pode estar em branco")
  @NotNull(message = "O campo nome não pode ser nulo")
  @Size(min = 6, max = 8, message = "Senha tem que ser de 6 a 8 caracteres!")
  private String password;

  @OneToOne
  @MapsId
  @JoinColumn(name = "agent_id")
  private Warehouse warehouse;

}
