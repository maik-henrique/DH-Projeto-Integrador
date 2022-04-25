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
@Entity(name = "section")
public class Section {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;

  private String name;

  private float capacity;

  @ManyToOne
  @JoinColumn(name = "warehouse", nullable = false)
  private Warehouse warehouse;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "category", nullable = false)
  private Category category;

}
