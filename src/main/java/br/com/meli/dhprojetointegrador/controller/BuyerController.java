package br.com.meli.dhprojetointegrador.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.meli.dhprojetointegrador.dto.request.FavoriteProductRequest;
import br.com.meli.dhprojetointegrador.service.BuyerService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/fresh-products/buyer")
@AllArgsConstructor

public class BuyerController {

  private final BuyerService BuyerService;

  /**
   * Author: Pedro Dalpa
   * Method: saveFavorite
   * Description: adiciona produto a tabela de favorito ligado a usuário
   *
   * @param favoriteProductRequest
   * 
   * @return status 201
   */
  @PostMapping("/favorite")
  public ResponseEntity<FavoriteProductRequest> saveFavorite(
      @RequestBody FavoriteProductRequest favoriteProductRequest) {
    BuyerService.saveFavorite(favoriteProductRequest);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  /**
   * Author: Pedro Dalpa
   * Method: deleteFavorite
   * Description: remove produto a tabela de favorito
   *
   * @param buyerId
   * @param productId
   * 
   * @return status 204
   */

  @DeleteMapping("/favorite/{buyerId}")
  public ResponseEntity<FavoriteProductRequest> deleteFavorite(
      @PathVariable Long buyerId,
      @RequestParam Long productId) {
    BuyerService.removeFavorite(buyerId, productId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
