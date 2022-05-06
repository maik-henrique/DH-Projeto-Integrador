package br.com.meli.dhprojetointegrador.entity;

import br.com.meli.dhprojetointegrador.enums.TokenTypeEnum;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken {
    private String token;
    private String expirationDate;
    private TokenTypeEnum tokenTypeEnum;
}
