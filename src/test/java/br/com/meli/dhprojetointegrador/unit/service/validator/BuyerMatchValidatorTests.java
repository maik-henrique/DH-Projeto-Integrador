package br.com.meli.dhprojetointegrador.unit.service.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.meli.dhprojetointegrador.entity.Buyer;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.service.validator.BuyerMatchValidator;

@ExtendWith(MockitoExtension.class)
public class BuyerMatchValidatorTests {
	
	private BuyerMatchValidator buyerMatchValidator;

	@Test
	public void validate_shouldThrowBusinessValidatorException_whenProvidedBuyerIsNotRelatedToPurchaseOrder() {
		Buyer expectedBuyer = Buyer.builder().id(1L).build();
		Buyer actualBuyer = Buyer.builder().id(32131L).build();
		
		PurchaseOrder purchaseOrder = PurchaseOrder.builder().buyer(actualBuyer).build();
		
		buyerMatchValidator = new BuyerMatchValidator(purchaseOrder, expectedBuyer);
		
		assertThrows(BusinessValidatorException.class, () -> buyerMatchValidator.validate());
	}
	
	@Test
	public void validate_shouldNotThrowException_whenBuyerIsRelated() {
		Buyer expectedBuyer = Buyer.builder().id(1L).build();
		PurchaseOrder purchaseOrder = PurchaseOrder.builder().buyer(expectedBuyer).build();
		
		buyerMatchValidator = new BuyerMatchValidator(purchaseOrder, expectedBuyer);
		
		assertDoesNotThrow(() -> buyerMatchValidator.validate());
	}
}
