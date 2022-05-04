package br.com.meli.dhprojetointegrador.dto.request;

import br.com.meli.dhprojetointegrador.entity.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgentPostRequest {
    @NotNull
    private String name;
    @NotNull
    private String password;
    @NotNull
    private Long warehouseId;
}