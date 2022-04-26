package br.com.meli.dhprojetointegrador.repository;


import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.meli.dhprojetointegrador.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory_Name(CategoryEnum category);
}
