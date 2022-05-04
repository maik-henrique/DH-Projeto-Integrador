package br.com.meli.dhprojetointegrador.repository;

import br.com.meli.dhprojetointegrador.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
}
