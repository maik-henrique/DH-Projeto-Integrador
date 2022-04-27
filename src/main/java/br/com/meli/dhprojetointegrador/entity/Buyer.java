package br.com.meli.dhprojetointegrador.entity;

import lombok.*;
import org.springframework.core.serializer.Serializer;

import javax.persistence.*;
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

    private String name;

    private String password;

    private String email;

}
