package br.com.meli.dhprojetointegrador.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
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
@Entity(name = "warehouse")
public class Warehouse {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;

  @NotBlank(message = "O campo nome não pode estar em branco")
  @NotNull(message = "O campo nome não pode ser nulo")
  private String name;

  @NotBlank(message = "O campo nome não pode estar em branco")
  @NotNull(message = "O campo nome não pode ser nulo")
  @OneToOne(mappedBy = "warehouse", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private Agent agent;
}
