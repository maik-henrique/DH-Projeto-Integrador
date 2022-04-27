package br.com.meli.dhprojetointegrador.service.validator;

import br.com.meli.dhprojetointegrador.entity.Buyer;
import br.com.meli.dhprojetointegrador.exception.BuyerNotFoundException;
import br.com.meli.dhprojetointegrador.repository.BuyerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ValidateBuyer {

    @Autowired
    private BuyerRepository buyerRepository;

    /**
     * Author: Bruno Mendes
     * Method: getBuyer
     * Description: Valida se determinado comprador existe no banco de dados
     */
    public Buyer getBuyer(Long id) {
        try {
            return buyerRepository.getById(id);
        } catch (EntityNotFoundException e) {
            System.out.println(e);
            throw new BuyerNotFoundException("This buyer is not on the database");
        }
    }
}
