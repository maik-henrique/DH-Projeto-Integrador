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

    public Buyer getBuyer(Long id) {
        try {
            return buyerRepository.findById(id).get();
        } catch (Exception e) {
            System.out.println(e);
            throw new BuyerNotFoundException("This buyer is not on the database");
        }
    }
}
