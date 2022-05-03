package br.com.meli.dhprojetointegrador.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class BatchStockUpdateRequest {
    @NotNull(message = "batchNumber must not be null")
    private Long batchNumber;

    @NotNull(message = "productId must not be null")
    private Long productId;

    @NotNull(message = "currentTemperature must not be null")
    private Float currentTemperature;

    @NotNull(message = "minimumTemperature must not be null")
    private Float minimumTemperature;

    @NotNull(message = "initialQuantity must not be null")
    private Integer initialQuantity;

    @NotNull(message = "currentQuantity must not be null")
    private Integer currentQuantity;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @NotNull(message = "dueDate must not be null")
    private LocalDate dueDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @NotNull(message = "manufacturingDate must not be null")
    private LocalDate manufacturingDate;

    @NotNull(message = "manufacturingTime must not be null")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime manufacturingTime;


}