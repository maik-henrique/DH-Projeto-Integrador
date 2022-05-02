package br.com.meli.dhprojetointegrador.mapper.evaluation;

import br.com.meli.dhprojetointegrador.dto.response.evaluation.PurchaseOrderEvaluationResponse;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrderEvaluation;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderEvaluationToPurchaseOrderEvaluationResponseConverter extends AbstractConverter<PurchaseOrderEvaluation, PurchaseOrderEvaluationResponse> {

    @Override
    protected PurchaseOrderEvaluationResponse convert(PurchaseOrderEvaluation source) {
        return PurchaseOrderEvaluationResponse
                .builder()
                .productId(source.getProduct().getId())
                .comment(source.getComment())
                .rating(source.getRating())
                .build();
    }
}
