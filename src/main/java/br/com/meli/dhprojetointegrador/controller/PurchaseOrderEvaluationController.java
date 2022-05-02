package br.com.meli.dhprojetointegrador.controller;

import br.com.meli.dhprojetointegrador.dto.request.evaluation.EvaluationUpdateRequest;
import br.com.meli.dhprojetointegrador.dto.request.evaluation.PurchaseOrderEvaluationRegistrationRequest;
import br.com.meli.dhprojetointegrador.dto.response.evaluation.ProductEvaluationDetailsResponse;
import br.com.meli.dhprojetointegrador.dto.response.evaluation.PurchaseOrderEvaluationFetchResponse;
import br.com.meli.dhprojetointegrador.dto.response.evaluation.PurchaseOrderEvaluationResponse;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrderEvaluation;
import br.com.meli.dhprojetointegrador.entity.view.PurchaseOrderEvaluationView;
import br.com.meli.dhprojetointegrador.service.PurchaseOrderEvaluationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/evaluation")
@AllArgsConstructor
public class PurchaseOrderEvaluationController {

    private final PurchaseOrderEvaluationService purchaseOrderEvaluationService;
    private final ModelMapper modelMapper;

    /**
     * Faz o registro da avaliação de um Buyer
     *
     * @author Maik
     * @param evaluationRegistrationRequest requisição de avaliação a ser registrada
     * @return dados da avaliação efetuada
     */
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody PurchaseOrderEvaluationRegistrationRequest evaluationRegistrationRequest) {
        PurchaseOrderEvaluation purchaseOrderEvaluation = modelMapper.map(evaluationRegistrationRequest, PurchaseOrderEvaluation.class);
        PurchaseOrderEvaluation savedPurchaseEvaluation = purchaseOrderEvaluationService.save(purchaseOrderEvaluation);
        PurchaseOrderEvaluationResponse response = modelMapper.map(savedPurchaseEvaluation, PurchaseOrderEvaluationResponse.class);
        URI location = URI.create("/" + purchaseOrderEvaluation.getId());
        return ResponseEntity.created(location).body(response);
    }

    /**
     * A partir de um buyer, lista todas as suas avaliações já efetuadas no sistema, caso o buyer não esteja registrado
     * ou não tenha feito nenhuma avaliação, então retorna 404
     * @author Maik
     * @param id identificação primária de um determinado buyer
     * @return lista de avaliações feitas por um buyer
     */
    @GetMapping("/buyer/{id}")
    public ResponseEntity<?> findEvaluationsByBuyerId(@PathVariable Long id) {
        List<PurchaseOrderEvaluation> evaluations = purchaseOrderEvaluationService.findEvaluationByBuyerId(id);
        List<PurchaseOrderEvaluationFetchResponse> response = evaluations.stream().map(evaluation -> modelMapper.map(evaluation, PurchaseOrderEvaluationFetchResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PatchMapping
    public ResponseEntity<?> updateEvaluation(@Valid @RequestBody EvaluationUpdateRequest evaluationUpdateRequest) {
        PurchaseOrderEvaluation purchaseOrderEvaluation = modelMapper.map(evaluationUpdateRequest, PurchaseOrderEvaluation.class);
        purchaseOrderEvaluationService.update(purchaseOrderEvaluation);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<?> findByProductId(@RequestParam Long productId) {
        PurchaseOrderEvaluationView evaluations = purchaseOrderEvaluationService.findByProductId(productId);
        ProductEvaluationDetailsResponse response = modelMapper.map(evaluations, ProductEvaluationDetailsResponse.class);
        return ResponseEntity.ok(response);
    }
}
