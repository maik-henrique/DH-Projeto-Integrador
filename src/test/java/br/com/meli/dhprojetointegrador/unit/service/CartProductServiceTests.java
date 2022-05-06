package br.com.meli.dhprojetointegrador.unit.service;

import br.com.meli.dhprojetointegrador.entity.CartProduct;
import br.com.meli.dhprojetointegrador.exception.ResourceNotFound;
import br.com.meli.dhprojetointegrador.repository.CartProductRepository;
import br.com.meli.dhprojetointegrador.service.CartProductService;
import br.com.meli.dhprojetointegrador.unit.util.CardProductCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;


public class CartProductServiceTests {

    CartProductRepository repository = mock(CartProductRepository.class);
    private final CartProductService service = new CartProductService(repository);
    List<CartProduct> cardProductList = IntStream
            .range(0, 2)
            .mapToObj(value -> {
                return CardProductCreator.createValidCardProduct();
            })
            .collect(Collectors.toList());

    /**
     * Author: Micaela Alves
     * Teste: Teste unitário req 002 - PI-10
     * Description: Validar a lista correta de produtos pertencentes a uma PurchaseOrder é retornada
     */
    @Test
    @DisplayName("TestPI-10 - getProductsByOrderId")
    public void getProductsByOrderId_should_return_correct_list() {
        long id = 1;
        Mockito.when(repository.findByPurchaseOrderId(id)).thenReturn(cardProductList);
        List<CartProduct> result = service.getProductsByOrderId(id);

        assert result.get(0).getQuantity().equals(5);
        assert result.get(0).getProduct().getName().equals("Frango");
        assert result.get(1).getPurchaseOrder().getBuyer().getName().equals("Bruno");


    }

    /**
     * Author: Micaela Alves
     * Teste: Teste unitário req 002 - PI-10
     * Description: Validar se um erro é retornado caso o PurchaseOrderId for invalido
     * @throws Exception
     */
    @Test
    @DisplayName("TestPI-10 - getProductsByOrderId - Exception")
    public void getProductsByOrderId_should_return_error_when_invalid_id() {
        Mockito.when(repository.findByPurchaseOrderId(99)).thenReturn(new ArrayList());
        assertThrows(ResourceNotFound.class, () -> service.getProductsByOrderId(99));
    }
}