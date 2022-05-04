package br.com.meli.dhprojetointegrador.repository;

import java.util.List;

import br.com.meli.dhprojetointegrador.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory_Name(CategoryEnum category);

    List<Product> findBySellerIdAndAndName(Long id, String name);

    List<Product> findBySeller(Seller seller);
}
