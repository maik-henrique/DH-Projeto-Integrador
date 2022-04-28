package br.com.meli.dhprojetointegrador.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "section")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  @NotNull(message = "O campo nome não pode ser nulo")
  @NotBlank(message = "O campo nome não pode estar em branco")
  private String name;

  @NotBlank(message = "O campo nome não pode estar em branco")
  @NotNull(message = "O campo nome não pode ser nulo")
  @NumberFormat
  private float capacity;

  @ManyToOne
  @JoinColumn(name = "warehouse", nullable = false)
  private Warehouse warehouse;

  @ManyToOne
  @JoinColumn(name = "category", nullable = false)
  private Category category;

}
