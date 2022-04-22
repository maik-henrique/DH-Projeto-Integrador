package br.com.meli.dhprojetointegrador.entity;

import br.com.meli.dhprojetointegrador.enums.StatusEnum;

import javax.persistence.*;
import java.time.LocalDate;

public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private LocalDate date;
}
