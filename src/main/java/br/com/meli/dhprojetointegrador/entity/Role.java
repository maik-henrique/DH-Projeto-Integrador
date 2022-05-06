package br.com.meli.dhprojetointegrador.entity;

import br.com.meli.dhprojetointegrador.enums.RoleEnum;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {

    private static final long serialVersionUID = -8566327587902012555L;

    @Id
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @ManyToMany(mappedBy = "role")
    private Set<User> user;

    @Override
    public String getAuthority() {
        return role.getRoleName();
    }
}
