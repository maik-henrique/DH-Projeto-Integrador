package br.com.meli.dhprojetointegrador.entity;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InboundOrder {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderNumber;
    private LocalDate orderDate;


    @ManyToOne
    @JoinColumn(name="agent_id", nullable=false)
    private Agent agent;

    // Section
    @ManyToOne
    @JoinColumn(name="section_id", nullable=false)
    private Agent agents;


    @OneToMany(mappedBy="inboundOrder")
    private List<BatchStock> batchStockList;



}
