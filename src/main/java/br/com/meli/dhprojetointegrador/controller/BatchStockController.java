package br.com.meli.dhprojetointegrador.controller;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.service.BatchStockService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/fresh-products")
@AllArgsConstructor
public class BatchStockController {

  private BatchStockService batchStockService;

  /**
   * Author: Pedro Dalpa
   * Method: filterStockBySection
   * Description: Busca estoque filtrando por seção e que a data de validade seja
   * maior que a de hoje somada com o parâmetro
   * 
   * 
   * @param sectionId    id of section to filter
   * @param numberOfDays goes to sum this param in current date to filter
   * @return list of batch stock that contains the filters
   * @throws BusinessValidatorException in case section not found
   *
   */

  @GetMapping("/due-date")
  public ResponseEntity<List<BatchStock>> filterStockBySection(@RequestParam(required = true) long sectionId,
      @RequestParam(defaultValue = "0", required = false) Integer numberOfDays,
      @RequestParam(defaultValue = "ASC", required = false) Direction ordination) {

    List<BatchStock> batchStocks = batchStockService.filterStockBySection(sectionId, numberOfDays, ordination);

    return ResponseEntity.ok().body(batchStocks);
  }
}
