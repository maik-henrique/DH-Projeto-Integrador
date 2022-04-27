package br.com.meli.dhprojetointegrador.entity;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class InboundOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer orderNumber;

    @NotNull(message = "O campo nome não pode ser nulo")
    @NotBlank(message = "O campo nome não pode estar em branco")
    @DateTimeFormat
    private LocalDate orderDate;

    @NotNull(message = "O campo nome não pode ser nulo")
    @NotBlank(message = "O campo nome não pode estar em branco")
    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    @NotNull(message = "O campo nome não pode ser nulo")
    @NotBlank(message = "O campo nome não pode estar em branco")
    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;


    @OneToMany(mappedBy = "inboundOrder")
    private Set<BatchStock> batchStockList;

}
