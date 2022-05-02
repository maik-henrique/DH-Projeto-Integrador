package br.com.meli.dhprojetointegrador.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.*;

import br.com.meli.dhprojetointegrador.enums.StatusEnum;
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
@Entity(name = "purchaseOrder")
public class PurchaseOrder implements Serializable {

    private static final long serialVersionUID = 7156526077883281623L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private LocalDate date;

    @OneToMany(mappedBy = "purchaseOrder", cascade = {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<PurchaseOrderEvaluation> evaluation;
    
    @OneToMany(mappedBy = "purchaseOrder")
    private Set<CartProduct> cartProduct;
}
