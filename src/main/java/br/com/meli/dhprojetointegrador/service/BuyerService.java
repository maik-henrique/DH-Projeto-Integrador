package br.com.meli.dhprojetointegrador.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.com.meli.dhprojetointegrador.dto.request.FavoriteProductRequest;
import br.com.meli.dhprojetointegrador.entity.Buyer;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.repository.BuyerRepository;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BuyerService {
  private BuyerRepository buyerRepository;
  private ProductRepository productRepository;

  /**
   * Author: Pedro Dalpa
   * Method: saveFavorite
   * Description: adiciona produto a tabela de favorito ligado a usuário,
   * verificando se o produto é valido e o usuario.
   *
   * @param favoriteProductRequest
   * 
   * @return void
   */

  public void saveFavorite(FavoriteProductRequest favoriteProductRequest) {
    Product product = productRepository.findById(favoriteProductRequest.getProductId())
        .orElseThrow(() -> new RuntimeException("No product found"));

    Buyer buyer = buyerRepository.findById(favoriteProductRequest.getProductId())
        .orElseThrow(() -> new RuntimeException("No buyer found"));

    Set<Product> products = new HashSet<>();
    products.add(product);

    products.addAll(buyer.getFavoriteProducts());

    buyer.setFavoriteProducts(products);

    buyerRepository.save(buyer);
  }

  /**
   * Author: Pedro Dalpa
   * Method: removeFavorite
   * Description: remove produto a tabela de favorito,
   * verificando se o produto é valido e o usuário.
   *
   * @param buyerId
   * @param productId
   * 
   * @return void
   */
  public void removeFavorite(Long buyerId, Long productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new RuntimeException("No product found"));

    Buyer buyer = buyerRepository.findById(buyerId)
        .orElseThrow(() -> new RuntimeException("No buyer found"));

    Set<Product> products = new HashSet<>();

    products.addAll(buyer.getFavoriteProducts());
    products.remove(product);

    buyer.setFavoriteProducts(products);

    buyerRepository.save(buyer);
  }
}
