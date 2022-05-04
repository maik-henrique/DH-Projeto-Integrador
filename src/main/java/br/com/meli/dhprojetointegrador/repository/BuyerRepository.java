package br.com.meli.dhprojetointegrador.repository;

import br.com.meli.dhprojetointegrador.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {

}
