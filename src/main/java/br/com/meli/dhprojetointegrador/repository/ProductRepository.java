package br.com.meli.dhprojetointegrador.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory_Name(CategoryEnum category);

    @Query("SELECT DISTINCT p FROM Product p JOIN p.batchStockList b WHERE p.price >= :minValue AND p.price <= :maxValue")
    List<Product> orderProductByPrice(
            @Param("minValue") BigDecimal minValue,
            @Param("maxValue") BigDecimal maxValue,
            Sort sort);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    List<Product> findByNameContainingIgnoreCase(@Param("name") String name);

    @Query("SELECT p FROM Product p WHERE p.brand LIKE %:brand%")
    List<Product> findByBrandContainingIgnoreCase(@Param("brand") String brand);
}
