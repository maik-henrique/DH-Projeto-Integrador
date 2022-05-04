package br.com.meli.dhprojetointegrador.entity;

import br.com.meli.dhprojetointegrador.enums.TokenType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken {
    private String token;
    private String expirationDate;
    private TokenType tokenType;
}
