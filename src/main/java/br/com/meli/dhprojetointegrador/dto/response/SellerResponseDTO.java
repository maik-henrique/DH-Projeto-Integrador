package br.com.meli.dhprojetointegrador.dto.response;

import br.com.meli.dhprojetointegrador.entity.Product;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class SellerResponseDTO {

    private Long id;
    private String name;
}
