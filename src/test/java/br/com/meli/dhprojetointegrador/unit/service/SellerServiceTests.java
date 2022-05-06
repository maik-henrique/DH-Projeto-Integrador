package br.com.meli.dhprojetointegrador.unit.service;

import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.Seller;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.exception.BuyerNotFoundException;
import br.com.meli.dhprojetointegrador.repository.SellerRepository;
import br.com.meli.dhprojetointegrador.service.SellerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SellerServiceTests {

    @InjectMocks
    private SellerService sellerService;

    private SellerRepository sellerRepository = mock(SellerRepository.class);

    Seller seller = Seller.builder().id(1l).name("Vendedor").statusActiveAccount(true).build();

    /**
     * @Author: Matheus Guerra
     * @Teste: Teste unitario função findSellerById
     * @Description: valida que a função retorna um seller com as mesmas propriedades do seller com o id
     * informado persistido no banco de dados
     */
    @Test
    @DisplayName("Test US:06 - findSellerById - Correct functioning")
    public void findSellerById_should_return_correct_seller(){
        when(sellerRepository.findById(1l)).thenReturn(Optional.of(seller));
        Seller result = sellerService.findSellerById(1l);

        assert result.equals(seller);
    }

    /**
     * @Author: Matheus Guerra
     * @Teste: Teste unitario função findSellerById
     * @Description: valida que a função retorna excecao caso nao haja um seller persistido com o id informado
     */
    @Test
    @DisplayName("Test US:06 - findSellerById - Generating exception")
    public void findSellerById_should_throw_correct_exception(){
        when(sellerRepository.findById(1l)).thenReturn(Optional.of(seller));

        try {
            sellerService.findSellerById(2l);
        } catch (BusinessValidatorException e) {
            assert true;
        }
    }

    /**
     * @Author: Matheus Guerra
     * @Teste: Teste unitario função saveSeller
     * @Description: valida que a função retorna um seller com as mesmas propriedades do seller salvo no repositorio
     */
    @Test
    @DisplayName("Test US:06 - saveSeller - Correct functioning")
    public void saveSeller_should_return_correct_seller(){
        when(sellerRepository.save(seller)).thenReturn(seller);
        Seller result = sellerService.saveSeller(seller);

        assert result.equals(seller);
    }

    /**
     * @Author: Matheus Guerra
     * @Teste: Teste unitario função putSellerName
     * @Description: valida que a função retorna o seller com o atributo name alterado corretamente
     */
    @Test
    @DisplayName("Test US:06 - putSellerName - Correct functioning")
    public void putSellerName_should_return_correct_seller(){
        when(sellerRepository.findById(1l)).thenReturn(Optional.of(seller));
        when(sellerRepository.save(seller)).thenReturn(seller);

        Seller result = sellerService.putSellerName(1l, "Novo vendedor");
        seller.setName("Novo vendedor");

        assert result.getName().equals("Novo vendedor");
        assert result.equals(seller);
    }

    /**
     * @Author: Matheus Guerra
     * @Teste: Teste unitario função putSellerAccountStatus
     * @Description: valida que a função retorna o seller com o atributo statusActiveAccount alterado corretamente
     */
    @Test
    @DisplayName("Test US:06 - putSellerAccountStatus - Correct functioning")
    public void putSellerAccountStatus_should_return_correct_seller(){
        when(sellerRepository.findById(1l)).thenReturn(Optional.of(seller));
        when(sellerRepository.save(seller)).thenReturn(seller);

        Seller result = sellerService.putSellerAccountStatus(1l, false);
        seller.setStatusActiveAccount(false);

        assert result.getStatusActiveAccount().equals(false);
        assert result.equals(seller);
    }

}
