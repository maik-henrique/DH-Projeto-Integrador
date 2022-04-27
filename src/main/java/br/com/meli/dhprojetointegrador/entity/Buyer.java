package br.com.meli.dhprojetointegrador.entity;

import lombok.*;
import org.springframework.core.serializer.Serializer;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "buyer")
public class Buyer implements Serializable{

    private static final long serialVersionUID = 7156526077883281623L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo nome não pode estar em branco")
    @NotNull(message = "O campo nome não pode ser nulo")
    private String name;

    @NotBlank(message = "O campo nome não pode estar em branco")
    @NotNull(message = "O campo nome não pode ser nulo")
    @Size(min = 6, max = 8, message = "Senha tem que ser de 6 a 8 caracteres!")
    private String password;

    @NotBlank(message = "O campo nome não pode estar em branco")
    @NotNull(message = "O campo nome não pode ser nulo")
    @Email
    private String email;

}
