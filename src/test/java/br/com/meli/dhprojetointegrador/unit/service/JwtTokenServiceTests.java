package br.com.meli.dhprojetointegrador.unit.service;

import br.com.meli.dhprojetointegrador.config.SecurityProperties;
import br.com.meli.dhprojetointegrador.entity.JwtToken;
import br.com.meli.dhprojetointegrador.entity.Role;
import br.com.meli.dhprojetointegrador.entity.User;
import br.com.meli.dhprojetointegrador.enums.RoleEnum;
import br.com.meli.dhprojetointegrador.exception.AuthException;
import br.com.meli.dhprojetointegrador.service.JwtTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtTokenServiceTests {
    @InjectMocks
    private JwtTokenService tokenService;
    @Mock
    private SecurityProperties securityProperties;

    @BeforeEach
    private void setup() {
        when(securityProperties.getSecretKey()).thenReturn("cebola");
        when(securityProperties.getTokenValidityInMinutes()).thenReturn(32L);
    }

    private static final User user = User.builder()
            .username("myemail@email.com")
            .password("12345")
            .id(1L)
            .role(Set.of(Role.builder().role(RoleEnum.ADMIN).build()))
            .build();

    @Test
    public void generateToken_shouldGenerateAndReturnJwtToken_WhenProperUserDetailsIsSent() {

        JwtToken generatedToken = tokenService.generateToken(user);

        assertNotNull(generatedToken);
        assertNotNull(generatedToken.getToken());
        assertNotNull(generatedToken.getExpirationDate());
    }

    @Test
    public void getUsernameFromToken_shouldReturnCorrespondingUser_whenValidTokenIsProvided() {
        JwtToken generatedToken = tokenService.generateToken(user);

        String usernameFromToken = tokenService.getUsernameFromToken(generatedToken.getToken());
        assertEquals(user.getUsername(), usernameFromToken);
    }

    @Test
    public void isTokenValid_shouldReturnTrue_whenUsernameMatchesAndTokenIsNotExpired() {
        JwtToken generatedToken = tokenService.generateToken(user);
        assertTrue(() -> tokenService.isTokenValid(generatedToken.getToken(), user));
    }

    @Test
    public void isTokenValid_shouldReturnFalse_whenUsernameDoesNotMatch() {
        User inputUser = User.builder()
                .username("moutro@email.com")
                .password("12345")
                .id(1L)
                .role(Set.of(Role.builder().role(RoleEnum.ADMIN).build()))
                .build();

        JwtToken generatedToken = tokenService.generateToken(user);

        assertThrows(AuthException.class, () -> tokenService.isTokenValid(generatedToken.getToken(), inputUser));
    }
}
