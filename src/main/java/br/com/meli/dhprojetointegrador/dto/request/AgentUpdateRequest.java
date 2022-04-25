package br.com.meli.dhprojetointegrador.dto.request;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class AgentUpdateRequest {
    private Integer id;
    private String name;
    private String password;

}
