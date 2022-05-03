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

    @NotNull(message = "O código batchNumber não pode ser nulo")
    private Long batchNumber;

    @NotNull(message = "O código produto não pode ser nulo")
    private Long productId;

    @NotNull(message = "O campo currentTemperature não pode ser nulo")
    private Float currentTemperature;

    @NotNull(message = "O campo minimumTemperature não pode ser nulo")
    private Float minimumTemperature;

    @NotNull(message = "O campo initialQuantity não pode ser nulo")
    private Integer initialQuantity;

    @NotNull(message = "O campo currentQuantity não pode ser nulo")
    private Integer currentQuantity;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @NotNull(message = "O campo dueDate não pode ser nulo")
    private LocalDate dueDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @NotNull(message = "O campo manufacturingDate não pode ser nulo")
    private LocalDate manufacturingDate;

    @NotNull(message = "O campo manufacturingTime não pode ser nulo")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime manufacturingTime;


}
