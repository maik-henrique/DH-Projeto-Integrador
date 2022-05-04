package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.entity.JwtToken;
import br.com.meli.dhprojetointegrador.entity.User;
import br.com.meli.dhprojetointegrador.exception.InvalidCredentialsException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Tem como objetivo ser um wrapper do UserDetailsManager, com foco em abstrair a autenticação e gerenciamento
 * do usuário para oferecer pontos de acesso voltados ao cadastro e login
 * @author Maik
 */
@Slf4j
@Service
@AllArgsConstructor
public class TokenAuthenticationService {
    private final ICustomUserDetailsService<User> userDetailsManager;
    private final ITokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Autnetica e retorna um token, caso contrário lança propaga exceções caso as credenciais fornecidas sejam inválidas
     *
     * @param user contém crendenciais e roles do usuário
     * @return JwtToken caso a autenticação seja bem sucedida
     */
    public JwtToken authenticate(User user) throws UsernameNotFoundException, InvalidCredentialsException {
        UserDetails userDetails = userDetailsManager.loadUserByUsername(user.getUsername());
        boolean isCredentialValid = validateCredentials(user, userDetails);

        if (!isCredentialValid) {
            log.info("Credentials provided by the request are invalid");
            throw new InvalidCredentialsException();
        }

        log.info("Credentials provided were successfully validated, returning token");

        return tokenService.generateToken(userDetails);
    }

    public void signUp(User user) {
        userDetailsManager.createUser(user);
    }


    private boolean validateCredentials(User user, UserDetails userDetails) {
        if (user == null || userDetails == null) {
            return false;
        }
        return passwordEncoder.matches(user.getPassword(), userDetails.getPassword());

    }
}
