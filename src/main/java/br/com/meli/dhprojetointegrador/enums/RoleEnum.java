package br.com.meli.dhprojetointegrador.enums;

import java.util.Arrays;

public enum RoleEnum {
    ADMIN("ROLE_ADMIN"),
    BUYER("ROLE_BUYER"),
    SELLER("ROLE_SELLER"),
    AGENT("ROLE_AGENT");

    private String roleName;

    public static RoleEnum valueOfIgnoreCase(String value) {
        return Arrays.stream(RoleEnum.values())
                .filter(v -> v.roleName.equals(value))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Role specified wasn`t found"));
    }

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
