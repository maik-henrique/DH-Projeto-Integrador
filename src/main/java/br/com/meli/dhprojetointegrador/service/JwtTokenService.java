package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.config.SecurityProperties;
import br.com.meli.dhprojetointegrador.entity.JwtToken;
import br.com.meli.dhprojetointegrador.enums.TokenTypeEnum;
import br.com.meli.dhprojetointegrador.exception.AuthException;
import br.com.meli.dhprojetointegrador.exception.ExpiredTokenException;
import br.com.meli.dhprojetointegrador.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Implementação do contrato para a operação de tokens JWT
 * @author Maik
 */
@Service
@AllArgsConstructor
public class JwtTokenService implements ITokenService{
    private final SecurityProperties securityProperties;

    /**
     * A partir de um usuário, gera um token assinado pela chave secreta definida nas configurações da aplicação e
     * que possui validade calculada como uma extensão em minutos do momento da criação
     * @param userDetails usuário que terá um token gerado
     * @return instância de JwtToken, com o token, data de expiração e seu tipo
     */
    @Override
    public JwtToken generateToken(UserDetails userDetails) {
        Instant issuetAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiration = issuetAt.plus(securityProperties.getTokenValidityInMinutes(), ChronoUnit.MINUTES);

        Map<String, Object> claims = new HashMap<>();

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(issuetAt))
                .setExpiration(Date.from(expiration))
                .signWith(SignatureAlgorithm.HS512, securityProperties.getSecretKey())
                .compact();

        return JwtToken.builder()
                .token(token)
                .expirationDate(expiration.toString())
                .tokenTypeEnum(TokenTypeEnum.BEARER)
                .build();
    }

    @Override
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token, Claims::getSubject);
    }

    /**
     * Valida um token a partir da sua descontrução, comparação com os dados de um usuário e checagem da sua expiração
     * @author Maik
     * @param token token que se deseja validar
     * @param userDetails usuário que será usado como base para comparação
     * @return true se o token é válido
     * @throws AuthException caso a validação do token falhe
     */
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) throws AuthException {
        final String username = getUsernameFromToken(token);
        boolean isValidUsername = username.equals(userDetails.getUsername());
        if (!isValidUsername) {
            throw new InvalidTokenException();
        }

        if (isTokenExpired(token)) {
            throw new ExpiredTokenException();
        }

        return true;
    }

    private boolean isTokenExpired(String token) {
        final Date expirationDate = getClaimsFromToken(token, Claims::getExpiration);
        final Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        return expirationDate.before(now);
    }

    private <T> T getClaimsFromToken(String token, Function<Claims, T> claimCallback) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimCallback.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(securityProperties.getSecretKey()).parseClaimsJws(token).getBody();
    }
}
