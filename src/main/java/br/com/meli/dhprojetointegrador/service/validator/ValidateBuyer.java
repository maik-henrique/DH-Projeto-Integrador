package br.com.meli.dhprojetointegrador.service.validator;

import br.com.meli.dhprojetointegrador.entity.Buyer;
import br.com.meli.dhprojetointegrador.exception.BuyerNotFoundException;
import br.com.meli.dhprojetointegrador.repository.BuyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
        } catch (Exception e) {
            System.out.println(e);
            throw new BuyerNotFoundException("This buyer is not on the database");
        }
    }
}
