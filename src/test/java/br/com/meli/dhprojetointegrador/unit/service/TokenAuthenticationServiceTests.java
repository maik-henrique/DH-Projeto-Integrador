package br.com.meli.dhprojetointegrador.unit.service;

import br.com.meli.dhprojetointegrador.entity.JwtToken;
import br.com.meli.dhprojetointegrador.entity.Role;
import br.com.meli.dhprojetointegrador.entity.User;
import br.com.meli.dhprojetointegrador.enums.RoleEnum;
import br.com.meli.dhprojetointegrador.exception.InvalidCredentialsException;
import br.com.meli.dhprojetointegrador.service.ICustomUserDetailsService;
import br.com.meli.dhprojetointegrador.service.ITokenService;
import br.com.meli.dhprojetointegrador.service.TokenAuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TokenAuthenticationServiceTests {
    @InjectMocks
    private TokenAuthenticationService tokenAuthenticationService;

    @Mock
    private ICustomUserDetailsService<User> userDetailsManager;

    @Mock
    private ITokenService tokenService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private static final User user = User.builder()
            .username("myemail@email.com")
            .password("12345")
            .id(1L)
            .role(Set.of(Role.builder().role(RoleEnum.ADMIN).build()))
            .build();

    @Test
    public void authenticate_shouldReturnToken_whenUserExists() {
        when(userDetailsManager.loadUserByUsername(any(String.class))).thenReturn(user);
        when(tokenService.generateToken(any()))
                .thenReturn(JwtToken.builder().token("token").expirationDate(Instant.now().toString()).build());
        when(passwordEncoder.matches(any(), any())).thenReturn(true);

        JwtToken jwtToken = tokenAuthenticationService.authenticate(user);
        assertNotNull(jwtToken);
        assertNotNull(jwtToken.getToken());
        assertNotNull(jwtToken.getExpirationDate());
    }

    @Test
    public void authenticate_shouldThrowInvalidCredentialsException_whenCredentialsDoNotMatch() {
        when(userDetailsManager.loadUserByUsername(any(String.class))).thenReturn(user);

        assertThrows(InvalidCredentialsException.class, () -> tokenAuthenticationService.authenticate(user));
    }

    @Test
    public void signUp_shouldProperlyCallUserManagerService_whenUserIsPassed() {
        tokenAuthenticationService.signUp(user);
        verify(userDetailsManager, times(1)).createUser(user);
    }

}
