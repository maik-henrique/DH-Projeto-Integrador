package br.com.meli.dhprojetointegrador.entity;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @NotNull(message = "O campo nome não pode ser nulo")
    private String name;

    
    @NotNull(message = "O campo nome não pode ser nulo")
    @Size(min = 6, max = 8, message = "Senha tem que ser de 6 a 8 caracteres!")
    private String password;

    
    @NotNull(message = "O campo nome não pode ser nulo")
    @Email
    private String email;

}
