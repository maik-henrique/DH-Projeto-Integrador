package br.com.meli.dhprojetointegrador.dto.request;

import lombok.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class AgentUpdateRequest {
    private Long id;
    private String name;
    private String password;

}
