package br.com.meli.dhprojetointegrador.unit.service;


import br.com.meli.dhprojetointegrador.entity.Role;
import br.com.meli.dhprojetointegrador.entity.User;
import br.com.meli.dhprojetointegrador.enums.RoleEnum;
import br.com.meli.dhprojetointegrador.repository.UserRepository;
import br.com.meli.dhprojetointegrador.service.JwtUserDetailsManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtUserDetailsManagerTests {

    @InjectMocks
    private JwtUserDetailsManager jwtUserDetailsManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private static final User user = User.builder()
            .username("myemail@email.com")
            .password("12345")
            .id(1L)
            .role(Set.of(Role.builder().role(RoleEnum.ADMIN).build()))
            .build();

    @Test
    public void loadUserByUsername_shouldThrowUsernameNotFoundException_whenUserIfNotFound() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        String userEmail = "email@email.com";

        assertThrows(UsernameNotFoundException.class, () -> jwtUserDetailsManager.loadUserByUsername(userEmail));
    }

    @Test
    public void loadUserByUsername_shouldReturnUser_whenUserIfFound() {
        String userEmail = "email@email.com";

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        UserDetails loadedUser = jwtUserDetailsManager.loadUserByUsername(userEmail);
        assertNotNull(loadedUser);
        assertEquals(user.getUsername(), loadedUser.getUsername());
        assertEquals(user.getPassword(), loadedUser.getPassword());
        assertEquals(user.getAuthorities(), loadedUser.getAuthorities());
    }

    @Test
    public void createUser_shouldReturnPersistedUser_whenProperUserDetailsAreProvided() {
        when(userRepository.save(any())).thenReturn(user);

        User createdUser = jwtUserDetailsManager.createUser(user);

        assertNotNull(createdUser);
        assertEquals(user.getUsername(), createdUser.getUsername());
        assertEquals(user.getId(), createdUser.getId());
        assertEquals(user.getAuthorities(), createdUser.getAuthorities());
    }

}
