
package br.com.meli.dhprojetointegrador.entity;

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

  private String name;

  @OneToOne
  @MapsId
  @JoinColumn(name = "agent_id")
  private Warehouse warehouse;

  @OneToOne
  @JoinColumn(name = "fk_user_id")
  private User user;

}
