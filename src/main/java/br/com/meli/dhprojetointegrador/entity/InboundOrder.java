package br.com.meli.dhprojetointegrador.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

@Entity(name = "inboundOrder")
public class InboundOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderNumber;
    private LocalDate orderDate;

    @ManyToOne
    @JoinColumn(name="agent_id", nullable=false)
    private Agent agent;

    @ManyToOne
    @JoinColumn(name="section_id", nullable=false)
    private Section section;

    @OneToMany(mappedBy="inboundOrder")
    private List<BatchStock> batchStockList;

}
