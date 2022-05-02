package br.com.meli.dhprojetointegrador.unit.service.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.meli.dhprojetointegrador.entity.CartProduct;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.service.validator.PurchaseOrderProductValidator;

@ExtendWith(MockitoExtension.class)
public class PurchaseOrderProductValidatorTests {
	
	@Test
	public void validate_shouldNotThrowException_whenProductMatches() {
		Product providedProduct = Product.builder().id(1L).build();
		Product randomProduct = Product.builder().id(22L).build();
		
		CartProduct cartProduct = CartProduct.builder().product(providedProduct).build();
		CartProduct randomCartProdcut = CartProduct.builder().product(randomProduct).build();
		
		PurchaseOrder queriedPurchaseOrder = PurchaseOrder.builder()
			.cartProduct(Set.of(cartProduct, randomCartProdcut)).build();
		
		PurchaseOrderProductValidator purchaseOrderProductValidator = new PurchaseOrderProductValidator(queriedPurchaseOrder, providedProduct);
		
		assertDoesNotThrow(() -> purchaseOrderProductValidator.validate());		
	}
	
	@Test
	public void validate_shouldThrowBusinessValidatorException_whenNoProductMatche() {
		Product providedProduct = Product.builder().id(1L).build();
		Product randomProduct = Product.builder().id(22L).build();		
		
		CartProduct randomCartProdcut = CartProduct.builder().product(randomProduct).build();
		
		PurchaseOrder queriedPurchaseOrder = PurchaseOrder.builder()
				.cartProduct(Set.of(randomCartProdcut)).build();
		
		PurchaseOrderProductValidator purchaseOrderProductValidator = new PurchaseOrderProductValidator(queriedPurchaseOrder, providedProduct);
		
		assertThrows(BusinessValidatorException.class, () -> purchaseOrderProductValidator.validate());		
	}

}
