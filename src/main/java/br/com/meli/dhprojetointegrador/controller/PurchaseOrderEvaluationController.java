package br.com.meli.dhprojetointegrador.controller;

import br.com.meli.dhprojetointegrador.dto.request.evaluation.PurchaseOrderEvaluationRegistrationRequest;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrderEvaluation;
import br.com.meli.dhprojetointegrador.service.PurchaseOrderEvaluationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/evaluation")
@AllArgsConstructor
public class PurchaseOrderEvaluationController {

    private final PurchaseOrderEvaluationService purchaseOrderEvaluationService;
    private final ModelMapper modelMapper;

    public ResponseEntity<?> save(@Valid @RequestBody PurchaseOrderEvaluationRegistrationRequest evaluationRegistrationRequest) {
        PurchaseOrderEvaluation purchaseOrderEvaluation = modelMapper.map(evaluationRegistrationRequest, PurchaseOrderEvaluation.class);
        purchaseOrderEvaluationService.save(purchaseOrderEvaluation);
        return ResponseEntity.ok(URI.create("/"));
    }
}
