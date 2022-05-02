package br.com.meli.dhprojetointegrador.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.meli.dhprojetointegrador.entity.Buyer;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrderEvaluation;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.repository.PurchaseOrderEvaluationRepository;
import br.com.meli.dhprojetointegrador.service.validator.BuyerMatchValidator;
import br.com.meli.dhprojetointegrador.service.validator.IPurchaseOrderEvaluationValidator;
import br.com.meli.dhprojetointegrador.service.validator.PurchaseOrderCompletedValidator;
import br.com.meli.dhprojetointegrador.service.validator.PurchaseOrderProductValidator;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PurchaseOrderEvaluationService {
	private final PurchaseOrderEvaluationRepository purchaseOrderEvaluationRepository;
	private final OrderService orderService;
	private List<IPurchaseOrderEvaluationValidator> validators;

	/**
	 * Usado para registrar uma avaliação feita pelo usuário para uma determinada compra completada
	 * @author Maik
	 * 
	 * @param purchaseOrderEvaluation avaliação do cliente que será salva 
	 * @return PurchaseOrderEvaluation avaliação que foi salva com sucesso
	 * @throws BusinessValidatorException caso alguma das validações falhe
	 */
	public PurchaseOrderEvaluation save(PurchaseOrderEvaluation purchaseOrderEvaluation)
			throws BusinessValidatorException {
		PurchaseOrder purchaseOrder = purchaseOrderEvaluation.getPurchaseOrder();

		PurchaseOrder registeredPurchaseOrder = orderService.findPurchaseOrderById(purchaseOrder.getId());
		initializeIInboundOrderValidators(purchaseOrderEvaluation, registeredPurchaseOrder);

		validators.forEach(validator -> validator.validate());

		return purchaseOrderEvaluationRepository.save(purchaseOrderEvaluation);
	}

	private void initializeIInboundOrderValidators(PurchaseOrderEvaluation purchaseOrderEvaluation,
			PurchaseOrder queriedPurchaseOrder) {
		Buyer buyer = purchaseOrderEvaluation.getPurchaseOrder().getBuyer();
		Product product = purchaseOrderEvaluation.getProduct();

		validators = List.of(new BuyerMatchValidator(queriedPurchaseOrder, buyer),
				new PurchaseOrderCompletedValidator(queriedPurchaseOrder),
				new PurchaseOrderProductValidator(queriedPurchaseOrder, product));

	}
}
