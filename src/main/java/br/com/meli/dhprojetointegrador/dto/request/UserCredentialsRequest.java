package br.com.meli.dhprojetointegrador.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialsRequest {

    @NotBlank
    @Size(min = 5)
    private String username;

    @NotBlank
    @Size(min = 5)
    private String password;
}
