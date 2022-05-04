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

    @NotBlank(message = "username must not be blank")
    @Size(min = 5, message = "username must have at least 5 characters")
    private String username;

    @NotBlank(message = "password must not be blank")
    @Size(min = 5, message = "password must have at least 5 characters")
    private String password;
}
