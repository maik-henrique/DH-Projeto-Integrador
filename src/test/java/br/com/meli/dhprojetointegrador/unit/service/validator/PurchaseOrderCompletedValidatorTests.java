package br.com.meli.dhprojetointegrador.unit.service.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.service.validator.PurchaseOrderCompletedValidator;

@ExtendWith(MockitoExtension.class)
public class PurchaseOrderCompletedValidatorTests {
	
	private PurchaseOrderCompletedValidator purchaseOrderCompletedValidator;

	@Test
	public void validate_shouldThrowBusinessValidatorException_whenStatusIsNotCompleted() {
		PurchaseOrder actualPurchaseOrder = PurchaseOrder.builder().status(StatusEnum.ABERTO).build();
		
		purchaseOrderCompletedValidator = new PurchaseOrderCompletedValidator(actualPurchaseOrder);
		
		assertThrows(BusinessValidatorException.class, () -> purchaseOrderCompletedValidator.validate());
	}
	
	@Test
	public void validate_shouldNotThrow_whenStatusIsompleted() {
		PurchaseOrder actualPurchaseOrder = PurchaseOrder.builder().status(StatusEnum.FINALIZADO).build();
		
		purchaseOrderCompletedValidator = new PurchaseOrderCompletedValidator(actualPurchaseOrder);
		
		assertDoesNotThrow(() -> purchaseOrderCompletedValidator.validate());
	}

}
