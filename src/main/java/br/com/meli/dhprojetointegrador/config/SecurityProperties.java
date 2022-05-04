package br.com.meli.dhprojetointegrador.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class SecurityProperties {

    @Value("${security.jwt.signing-key}")
    private String secretKey;

    @Value("${security.jwt.token-duration-in-minutes}")
    private Long tokenValidityInMinutes;
}
