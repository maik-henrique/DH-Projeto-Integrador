package br.com.meli.dhprojetointegrador.entity;

import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BatchStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchNumber;

    private int currentQuantity;
    private LocalDate dueDate;
    private int initialQuantity;
    private LocalDate manufacturingDate;
    private LocalDateTime manufacturingTime;
    private float currentTemperature;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product products;

    @ManyToOne
    @JoinColumn(name = "order_number")
    private InboundOrder inboundOrder;
}
