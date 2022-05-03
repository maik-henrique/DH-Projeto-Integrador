package br.com.meli.dhprojetointegrador.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "batch_stock")
public class BatchStock {

    @ApiModelProperty(value = "Código do bactchStock")
    @Id
    private Long batchNumber;

    @NumberFormat
    private int currentQuantity;

    @NotNull(message = "O campo dueDate não pode ser nulo")
    private LocalDate dueDate;

    @NumberFormat
    private int initialQuantity;

    @NotNull(message = "O campo manufacturingDate não pode ser nulo")
    private LocalDate manufacturingDate;

    @NotNull(message = "O campo manufacturingTime não pode ser nulo")
    private LocalDateTime manufacturingTime;
    private float currentTemperature;
    private float minimumTemperature;

    @ApiModelProperty(value = "nome do produto")
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private Product products;

    @ApiModelProperty(value = "inbounOrder Number")
    @ManyToOne
    @JoinColumn(name = "order_number")
    @JsonIgnore
    private InboundOrder inboundOrder;
}
