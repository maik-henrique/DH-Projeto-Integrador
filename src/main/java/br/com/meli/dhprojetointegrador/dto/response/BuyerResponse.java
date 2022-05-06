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
public class BuyerResponse {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String password;

    @NotNull
    @Email
    private String email;

    @NotNull
    private  BuyerStatusEnum statusEnum;

    public static BuyerResponse map(Buyer buyer) {
        return BuyerResponse.builder().name(buyer.getEmail()).name(buyer.getName()).build();
    }

}
