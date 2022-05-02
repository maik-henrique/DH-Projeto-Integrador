package br.com.meli.dhprojetointegrador.dto.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class FavoriteProductRequest {
  @NotNull
  private Long buyerId;
  @NotNull
  private Long productId;

}
