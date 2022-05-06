package br.com.meli.dhprojetointegrador.service.validator;

import br.com.meli.dhprojetointegrador.entity.Buyer;
import br.com.meli.dhprojetointegrador.exception.BuyerNotFoundException;
import br.com.meli.dhprojetointegrador.repository.BuyerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class BuyerValidator {

    @Autowired
    private BuyerRepository buyerRepository;

    /**
     * Author: Bruno Mendes
     * Method: getBuyer
     * Description: Valida se determinado comprador existe no banco de dados
     */
    //@CachePut(value = "getBuyer",key = "#id")
    public Buyer getBuyer(Long id) {
        try {
            Buyer buyer = buyerRepository.findById(id).get();
            return buyer;
        } catch (NoSuchElementException e) {
            throw new BuyerNotFoundException("This buyer is not on the database");
        }
    }
}
