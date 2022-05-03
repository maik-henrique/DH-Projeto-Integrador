package br.com.meli.dhprojetointegrador.entity;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import br.com.meli.dhprojetointegrador.enums.BuyerStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "buyer")
public class Buyer implements Serializable {

    private static final long serialVersionUID = 7156526077883281623L;

    @ApiModelProperty(value = "Código do buyer")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @NotNull(message = "O campo name não pode ser nulo")
    private String name;

    
    @NotNull(message = "O campo password não pode ser nulo")
    @Size(min = 6, max = 8, message = "Senha tem que ser de 6 a 8 caracteres!")
    private String password;

    
    @NotNull(message = "O campo email não pode ser nulo")
    @Email
    private String email;

    @NotNull(message = "O campo status não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private BuyerStatusEnum status;

}
