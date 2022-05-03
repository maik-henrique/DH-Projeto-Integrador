package br.com.meli.dhprojetointegrador.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class InboundOrder {

    @ApiModelProperty(value = "Código da OrderNumber")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderNumber;

    @NotNull(message = "O campo nome não pode ser nulo")
    private LocalDate orderDate;

    @ApiModelProperty(value = "Código do agent")
    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    @ApiModelProperty(value = "Código da section")
    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    @OneToMany(mappedBy = "inboundOrder", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<BatchStock> batchStockList;

    @PrePersist
    public void prePersist() {
        if (batchStockList != null) {
            batchStockList.forEach(b -> b.setInboundOrder(this));
        }
    }

}
