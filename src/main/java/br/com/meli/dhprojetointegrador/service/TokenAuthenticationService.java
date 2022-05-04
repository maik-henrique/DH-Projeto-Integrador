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

@Slf4j
@Service
@AllArgsConstructor
public class TokenAuthenticationService {
    private final ICustomUserDetailsService<User> userDetailsManager;
    private final ITokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Authenticates and returns a token, otherwise it throws an exception if the user's account is not enabled or credentials aren't valid
     * @param user UserDetails, containing it's credentials
     * @return JWT token
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
