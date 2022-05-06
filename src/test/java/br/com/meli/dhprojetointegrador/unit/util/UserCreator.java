package br.com.meli.dhprojetointegrador.unit.util;

import br.com.meli.dhprojetointegrador.entity.Agent;
import br.com.meli.dhprojetointegrador.entity.Role;
import br.com.meli.dhprojetointegrador.entity.User;
import br.com.meli.dhprojetointegrador.enums.RoleEnum;

public class UserCreator {

    public static User createUserAdmin() {
        return User.builder()
                .id(1L)
                .username("USER")
                .password("12345")
                .build();
    }
}
