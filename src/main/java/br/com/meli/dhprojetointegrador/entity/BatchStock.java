package br.com.meli.dhprojetointegrador.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

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

    @NumberFormat
    private int currentQuantity;

    @NotNull(message = "O campo nome não pode ser nulo")
    private LocalDate dueDate;

    @NumberFormat
    private int initialQuantity;

    @NotNull(message = "O campo nome não pode ser nulo")
    private LocalDate manufacturingDate;

    @NotNull(message = "O campo nome não pode ser nulo")
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
