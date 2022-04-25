package br.com.meli.dhprojetointegrador.dto.request;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class InboundOrderUpdateRequest {

    @NotNull
    private Integer orderNumber;

    @NotNull
    private LocalDate orderDate;

    @NotNull
    private Integer sectionId;

    @NotNull
    private Integer agentId;
    @NotNull
    private List<@Valid BatchStockUpdateRequest> batchStock;
}
