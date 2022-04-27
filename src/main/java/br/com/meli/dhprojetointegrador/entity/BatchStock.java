package br.com.meli.dhprojetointegrador.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "batch_stock")
public class BatchStock {
    @Id
    private Long batchNumber;

    private int currentQuantity;
    private LocalDate dueDate;
    private int initialQuantity;
    private LocalDate manufacturingDate;
    private LocalDateTime manufacturingTime;
    private float currentTemperature;
    private float minimumTemperature;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product products;

    @ManyToOne
    @JoinColumn(name = "order_number")
    private InboundOrder inboundOrder;
}
