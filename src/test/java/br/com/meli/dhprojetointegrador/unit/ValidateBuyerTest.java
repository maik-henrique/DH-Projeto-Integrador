package br.com.meli.dhprojetointegrador.unit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.meli.dhprojetointegrador.entity.Buyer;
import br.com.meli.dhprojetointegrador.exception.BuyerNotFoundException;
import br.com.meli.dhprojetointegrador.repository.BuyerRepository;
import br.com.meli.dhprojetointegrador.service.validator.ValidateBuyer;

public class ValidateBuyerTest {

    BuyerRepository buyerRepository = mock(BuyerRepository.class);

    private ValidateBuyer validateBuyer = new ValidateBuyer(buyerRepository);

    Buyer buyer = Buyer.builder()
            .id(1L)
            .name("Bruno")
            .password("123456")
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

        Buyer result = validateBuyer.getBuyer(1L);

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
            validateBuyer.getBuyer(2L);
        } catch (BuyerNotFoundException e) {
            assert true;
        }
    }
}
