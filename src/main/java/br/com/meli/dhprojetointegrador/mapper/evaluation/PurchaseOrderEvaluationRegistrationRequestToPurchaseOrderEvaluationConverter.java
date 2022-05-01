package br.com.meli.dhprojetointegrador.mapper.evaluation;

import br.com.meli.dhprojetointegrador.dto.request.evaluation.PurchaseOrderEvaluationRegistrationRequest;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrderEvaluation;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderEvaluationRegistrationRequestToPurchaseOrderEvaluationConverter
        extends AbstractConverter<PurchaseOrderEvaluationRegistrationRequest, PurchaseOrderEvaluation> {
    @Override
    protected PurchaseOrderEvaluation convert(PurchaseOrderEvaluationRegistrationRequest source) {


        return PurchaseOrderEvaluation
                .builder()
                .
                .comment(source.g)
                .build();
    }
}
