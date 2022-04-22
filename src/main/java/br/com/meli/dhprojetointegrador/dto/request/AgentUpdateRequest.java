package br.com.meli.dhprojetointegrador.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Builder
public class AgentUpdateRequest {
    private Integer id;
    private String name;
    private String password;

}
