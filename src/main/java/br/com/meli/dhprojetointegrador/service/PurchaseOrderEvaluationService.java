package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.entity.Buyer;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrderEvaluation;
import br.com.meli.dhprojetointegrador.entity.view.PurchaseOrderEvaluationView;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.exception.ResourceNotFoundException;
import br.com.meli.dhprojetointegrador.repository.PurchaseOrderEvaluationRepository;
import br.com.meli.dhprojetointegrador.service.validator.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PurchaseOrderEvaluationService {
    private final PurchaseOrderEvaluationRepository purchaseOrderEvaluationRepository;
    private final OrderService orderService;
    private List<IPurchaseOrderEvaluationValidator> validators;

    /**
     * Usado para registrar uma avaliação feita pelo usuário para uma determinada compra completada
     *
     * @param purchaseOrderEvaluation avaliação do cliente que será salva
     * @return PurchaseOrderEvaluation avaliação que foi salva com sucesso
     * @throws BusinessValidatorException caso alguma das validações falhe
     * @author Maik
     */
    public PurchaseOrderEvaluation save(PurchaseOrderEvaluation purchaseOrderEvaluation)
            throws BusinessValidatorException {
        PurchaseOrder purchaseOrder = purchaseOrderEvaluation.getPurchaseOrder();
        PurchaseOrder registeredPurchaseOrder = orderService.findPurchaseOrderById(purchaseOrder.getId());

        initializeIInboundOrderValidators(purchaseOrderEvaluation, registeredPurchaseOrder);
        validators.forEach(IPurchaseOrderEvaluationValidator::validate);

        return purchaseOrderEvaluationRepository.save(purchaseOrderEvaluation);
    }

    /**
     * Lista e retorna caso encontre todas as avaliações já efetuadas pelo buyer, caso não encontre nenhuma ou o
     * buyer não exista, lança uma exceção
     *
     * @author Maik
     * @param id identificação do Buyer, refere-se a sua chave primária
     * @return lista de avaliações feitas pelo buyer
     * @throws ResourceNotFoundException caso nenhuma avaliação feita pelo buyer seja encontrada
     */
    public List<PurchaseOrderEvaluation> findEvaluationByBuyerId(Long id) throws ResourceNotFoundException {
        List<PurchaseOrderEvaluation> buyerEvaluations = purchaseOrderEvaluationRepository.findByBuyerId(id);

        if (buyerEvaluations.isEmpty()) {
            throw new ResourceNotFoundException(String.format("No evaluation was found for buyer of id %d", id));
        }

        return buyerEvaluations;
    }

    /**
     * Atualiza o comentário de uma avaliação de um dado comprador
     *
     * @author Maik
     * @param purchaseOrderEvaluation avaliação que será com os campos atualizados
     * @throws ResourceNotFoundException caso nenhuma avaliação correspondente seja encontrada
     */
    public void update(PurchaseOrderEvaluation purchaseOrderEvaluation) throws ResourceNotFoundException {
        Buyer buyer = purchaseOrderEvaluation.getPurchaseOrder().getBuyer();
        PurchaseOrderEvaluation oldPurchaseOrder = findByIdAndBuyerId(purchaseOrderEvaluation.getId(), buyer.getId());

        oldPurchaseOrder.setComment(purchaseOrderEvaluation.getComment());

        purchaseOrderEvaluationRepository.save(oldPurchaseOrder);
    }

    public PurchaseOrderEvaluationView findByProductId(Long productId) {
        List<PurchaseOrderEvaluation> purchaseOrderEvaluations = purchaseOrderEvaluationRepository.findByProductId(productId);

        if (purchaseOrderEvaluations.isEmpty()) {
            throw new ResourceNotFoundException(String.format("No evaluations were found that refered to the product of id %d", productId));
        }

        double averageRate = purchaseOrderEvaluations.stream()
                .map(PurchaseOrderEvaluation::getRating)
                .mapToDouble(Integer::doubleValue)
                .average().orElseThrow(() ->
						new RuntimeException(String.format("No rate was present to calculate the rate for the product of id %d", productId)));

        return PurchaseOrderEvaluationView.builder()
                .evaluations(purchaseOrderEvaluations)
                .averageRate(averageRate)
                .build();
    }

    private PurchaseOrderEvaluation findByIdAndBuyerId(Long id, Long buyerId) throws ResourceNotFoundException {
        return purchaseOrderEvaluationRepository.findByIdAndBuyerId(id, buyerId)
                .orElseThrow(() -> new ResourceNotFoundException(String
                        .format("No evaluation found with id %d that was done by a buyer of id %d", id, buyerId)));
    }

    private void initializeIInboundOrderValidators(PurchaseOrderEvaluation purchaseOrderEvaluation,
                                                   PurchaseOrder queriedPurchaseOrder) {
        Buyer buyer = purchaseOrderEvaluation.getPurchaseOrder().getBuyer();
        Product product = purchaseOrderEvaluation.getProduct();

        validators = List.of(new PurchaseAlreadyEvaluatedValidator(purchaseOrderEvaluationRepository, product,
                        queriedPurchaseOrder),
                new BuyerMatchValidator(queriedPurchaseOrder, buyer),
                new PurchaseOrderCompletedValidator(queriedPurchaseOrder),
                new PurchaseOrderProductValidator(queriedPurchaseOrder, product));
    }

}
