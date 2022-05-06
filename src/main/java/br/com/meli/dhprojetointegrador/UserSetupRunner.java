package br.com.meli.dhprojetointegrador;

import br.com.meli.dhprojetointegrador.entity.Role;
import br.com.meli.dhprojetointegrador.entity.User;
import br.com.meli.dhprojetointegrador.enums.RoleEnum;
import br.com.meli.dhprojetointegrador.service.JwtUserDetailsManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class UserSetupRunner implements CommandLineRunner {

    private final JwtUserDetailsManager jwtUserDetailsManager;

    public UserSetupRunner(JwtUserDetailsManager jwtUserDetailsManager) {
        this.jwtUserDetailsManager = jwtUserDetailsManager;
    }

    @Override
    public void run(String... args) throws Exception {
        User agentUser = getAgentUser();
        createUser(agentUser);
        User sellerUser = getSeller();
        createUser(sellerUser);
        User buyer = getBuyer();
        createUser(buyer);
    }

    private void createUser(User user) {
        try {
            jwtUserDetailsManager.createUser(user);
        } catch (RuntimeException e) {}
    }

    private static User getAgentUser() {
        Set<Role> roles = Set.of(Role.builder().role(RoleEnum.AGENT).build());
        return User
                .builder()
                .username("agent")
                .password("agent")
                .role(roles).build();
    }

    private static User getSeller() {
        Set<Role> roles = Set.of(Role.builder().role(RoleEnum.SELLER).build());
        return User
                .builder()
                .username("seller")
                .password("seller")
                .role(roles).build();
    }

    private static User getBuyer() {
        Set<Role> roles = Set.of(Role.builder().role(RoleEnum.BUYER).build());
        return User
                .builder()
                .username("buyer")
                .password("buyer")
                .role(roles).build();
    }


}
