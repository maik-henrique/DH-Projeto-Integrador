package br.com.meli.dhprojetointegrador.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.meli.dhprojetointegrador.dto.request.FavoriteProductRequest;
import br.com.meli.dhprojetointegrador.service.BuyerService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/fresh-products/buyer")
@AllArgsConstructor

public class BuyerController {

  private final BuyerService BuyerService;

  @PostMapping("/favorite")
  public ResponseEntity<FavoriteProductRequest> favorite(@RequestBody FavoriteProductRequest favoriteProductRequest) {
    BuyerService.saveFavorite(favoriteProductRequest);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
