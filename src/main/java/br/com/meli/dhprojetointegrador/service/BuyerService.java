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
   * Author: Micaela Alves
   * Method: getProductsByOrderId
   * Description: Recupera uma lista com todos os registros de CartProduct onde
   * OrderId for igual ao especificado
   *
   **/
  public void saveFavorite(FavoriteProductRequest favoriteProductRequest) {
    Product product = productRepository.findById(favoriteProductRequest.getProductId())
        .orElseThrow(() -> new RuntimeException("No Batchstock found"));

    Buyer buyer = buyerRepository.findById(favoriteProductRequest.getProductId())
        .orElseThrow(() -> new RuntimeException("No Batchstock found"));

    Set<Product> products = new HashSet<>();
    products.add(product);

    products.addAll(buyer.getFavoriteProducts());

    buyer.setFavoriteProducts(products);

    buyerRepository.save(buyer);
  }
}
