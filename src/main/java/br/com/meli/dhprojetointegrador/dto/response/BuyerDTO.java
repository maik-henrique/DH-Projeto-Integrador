package br.com.meli.dhprojetointegrador.dto.response;

import br.com.meli.dhprojetointegrador.entity.Buyer;
import br.com.meli.dhprojetointegrador.enums.BuyerStatusEnum;
import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class BuyerDTO {

    @NotNull(message = "O campo id não pode ser nulo")
    private Long id;

    @NotNull(message = "O campo nome não pode ser nulo")
    private String name;

    @NotNull(message = "O campo password não pode ser nulo")
    private String password;

    @NotNull(message = "O campo email não pode ser nulo")
    @Email
    private String email;

    @NotNull(message = "O campo status não pode ser nulo")
    private  BuyerStatusEnum statusEnum;

    public static BuyerDTO map(Buyer buyer) {
        return BuyerDTO.builder().name(buyer.getEmail()).name(buyer.getName()).build();
    }

}
