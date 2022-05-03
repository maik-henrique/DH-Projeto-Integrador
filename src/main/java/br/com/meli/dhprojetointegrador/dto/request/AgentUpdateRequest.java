package br.com.meli.dhprojetointegrador.dto.request;

import lombok.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class AgentUpdateRequest {

    
    @NotNull(message = "O código id não pode ser nulo")
    private Long id;

    @NotNull(message = "O campo name não pode ser nulo")
    private String name;

    @NotNull(message = "O campo password não pode ser nulo")
    private String password;



}
