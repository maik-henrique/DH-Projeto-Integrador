package br.com.meli.dhprojetointegrador.service.validator;

import java.util.Set;

import br.com.meli.dhprojetointegrador.entity.CartProduct;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import lombok.AllArgsConstructor;

/**
 * @author Maik
 * Valida se o PucharseOrder possui o produto especificado
 */
@AllArgsConstructor
public class PurchaseOrderProductValidator implements IPurchaseOrderEvaluationValidator {

	private final PurchaseOrder queriedPurchaseOrder;
	private final Product providedProduct;

	/**
	 * @author Maik
	 * Valida em algum CartProduct do PurchaseOrder há um Product com o mesmo identificador do Product alvo, caso não
	 * lança a exceção BusinessValidatorExeptionS
	 */
	@Override
	public void validate() throws BusinessValidatorException {
		Long productId = providedProduct.getId();
		Set<CartProduct> carts = queriedPurchaseOrder.getCartProduct();

		carts.stream().map(CartProduct::getProduct)
			.filter(product -> product.getId().equals(productId))
			.findAny()
			.orElseThrow(() -> new BusinessValidatorException(
						String.format("Product of id %d is not linked to the purchase order", productId)));
	}

}
