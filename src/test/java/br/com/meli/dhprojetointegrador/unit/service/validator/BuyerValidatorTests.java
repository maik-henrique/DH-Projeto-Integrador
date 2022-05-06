package br.com.meli.dhprojetointegrador.unit.service.validator;

import br.com.meli.dhprojetointegrador.entity.Buyer;
import br.com.meli.dhprojetointegrador.exception.BuyerNotFoundException;
import br.com.meli.dhprojetointegrador.repository.BuyerRepository;
import br.com.meli.dhprojetointegrador.service.validator.BuyerValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BuyerValidatorTests {

    BuyerRepository buyerRepository = mock(BuyerRepository.class);

    private BuyerValidator buyerValidator = new BuyerValidator(buyerRepository);

    Buyer buyer = Buyer.builder()
            .id(1L)
            .name("Bruno")
            .email("bruno@email.com")
            .build();

    /**
     * @Author: Bruno
     * @Teste: Teste unitario função getBuyer
     * @Description: valida funcionamento correto da função
     */
    @Test
    @DisplayName("TestPI-8 - validateQuantity")
    public void validateBuyer_should_return_correct_Buyer() {
        when(buyerRepository.findById(1L)).thenReturn(Optional.of(buyer));

        Buyer result = buyerValidator.getBuyer(1L);

        assert result.equals(buyer);
    }

    /**
     * @Author: Bruno
     * @Teste: Teste unitario função getBuyer
     * @Description: valida funcionamento correto da função
     */
    @Test
    @DisplayName("TestPI-8 - validateQuantity")
    public void validateBuyer_should_trow_correct_Error() {
        when(buyerRepository.findById(2L)).thenThrow(new NoSuchElementException());

        try {
            buyerValidator.getBuyer( 2L);
        } catch (BuyerNotFoundException e) {
            assert true;
        }
    }
}
