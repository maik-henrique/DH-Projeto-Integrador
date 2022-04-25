package br.com.meli.dhprojetointegrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.meli.dhprojetointegrador.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
