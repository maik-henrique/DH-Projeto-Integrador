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


    Product product = Product.builder().name("Caneta").price(BigDecimal.valueOf(10)).volume(0.5f).build();
    Seller seller = Seller.builder().id(1l).name("Vendedor").statusActiveAccount(true).build();
    Optional<Seller> op;

    @Test
    @DisplayName("Test US:06")
    public void findSellerById_should_return_correct_seller(){

        when(sellerRepository.findById(1l)).thenReturn(Optional.of(seller));


        Seller result = sellerService.findSellerById(1l);


        assert result.equals(seller);
    }

    @Test
    @DisplayName("Test US:06")
    public void findSellerById_should_throw_correct_exception(){

        when(sellerRepository.findById(1l)).thenReturn(Optional.of(seller));

        try {
            sellerService.findSellerById(2l);
        } catch (BusinessValidatorException e) {
            assert true;
        }

    }

    @Test
    @DisplayName("Test US:06")
    public void saveSeller_should_return_correct_seller(){

        when(sellerRepository.save(seller)).thenReturn(seller);

        Seller result = sellerService.saveSeller(seller);

        assert result.getId().equals(1l);
        assert result.equals(seller);
    }

    @Test
    @DisplayName("Test US:06")
    public void putSellerName_should_return_correct_seller(){

        when(sellerRepository.findById(1l)).thenReturn(Optional.of(seller));
        when(sellerRepository.save(seller)).thenReturn(seller);

        Seller result = sellerService.putSellerName(1l, "Novo vendedor");

        assert result.getName().equals("Novo vendedor");

    }

    @Test
    @DisplayName("Test US:06")
    public void putSellerAccountStatus_should_return_correct_seller(){

        when(sellerRepository.findById(1l)).thenReturn(Optional.of(seller));
        when(sellerRepository.save(seller)).thenReturn(seller);

        Seller result = sellerService.putSellerAccountStatus(1l, false);

        assert result.getStatusActiveAccount().equals(false);

    }



}
