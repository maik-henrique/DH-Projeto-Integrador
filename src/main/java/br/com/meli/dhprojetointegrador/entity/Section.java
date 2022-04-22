package br.com.meli.dhprojetointegrador.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
  private Integer id;

  private String name;

  private float capacity;

  @ManyToOne
  @JoinColumn(name = "warehouse", nullable = false)
  private Warehouse warehouse;

  @ManyToOne
  @JoinColumn(name = "category", nullable = false)
  private Category category;

}
