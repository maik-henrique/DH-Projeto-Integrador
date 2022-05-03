package br.com.meli.dhprojetointegrador.dto.request;


import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderInput {

    @NotNull(message = "O campo date não pode ser nulo")
    @DateTimeFormat
    private LocalDate date;

    @NotNull(message = "O código buyerId não pode ser nulo")
    @NumberFormat
    private Long buyerId;

    @NotNull(message = "O campo orderStatus não pode ser nulo")
    private StatusEnum orderStatus;

    @NotNull(message = "O campo products não pode ser nulo")
    private List<@Valid ProductInput> products;


}
