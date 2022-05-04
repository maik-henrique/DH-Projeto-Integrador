package br.com.meli.dhprojetointegrador.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class SellerPostRequest {

    @NotNull(message = "name must not be null")
    private String name;

    private Boolean statusActiveAccount = true;
}
