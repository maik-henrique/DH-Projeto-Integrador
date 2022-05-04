package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.entity.JwtToken;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Define contrato para operações relacionadas à token, como validação e geração
 * @author Maik
 */
public interface ITokenService {
    String getUsernameFromToken(String token);
    JwtToken generateToken(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
}
