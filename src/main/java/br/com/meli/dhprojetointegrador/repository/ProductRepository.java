package br.com.meli.dhprojetointegrador.repository;

import br.com.meli.dhprojetointegrador.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
