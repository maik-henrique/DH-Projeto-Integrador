package br.com.meli.dhprojetointegrador.mapper.evaluation;

import br.com.meli.dhprojetointegrador.dto.request.evaluation.EvaluationDetailsRegistrationRequest;
import br.com.meli.dhprojetointegrador.dto.request.evaluation.PurchaseOrderEvaluationRegistrationRequest;
import br.com.meli.dhprojetointegrador.entity.Buyer;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrderEvaluation;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderEvaluationRegistrationRequestToPurchaseOrderEvaluationConverter
        extends AbstractConverter<PurchaseOrderEvaluationRegistrationRequest, PurchaseOrderEvaluation> {
    @Override
    protected PurchaseOrderEvaluation convert(PurchaseOrderEvaluationRegistrationRequest source) {
        PurchaseOrder purchaseOrder = PurchaseOrder.builder().id(source.getPurchaseOrderId())
                .buyer(Buyer.builder().id(source.getBuyerId()).build()).build();
        Product product = Product.builder().id(source.getEvaluation().getProductId()).build();


        EvaluationDetailsRegistrationRequest evaluation = source.getEvaluation();
        Integer rating = evaluation.getRating();
        String comment = evaluation.getComment();

        return PurchaseOrderEvaluation.builder()
				.comment(comment)
				.purchaseOrder(purchaseOrder)
				.rating(rating)
				.product(product)
				.build();
    }
}
