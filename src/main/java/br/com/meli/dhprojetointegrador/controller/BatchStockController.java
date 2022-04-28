package br.com.meli.dhprojetointegrador.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.service.BatchStockService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/fresh-products")
@AllArgsConstructor
public class BatchStockController {

  private BatchStockService batchStockService;

  @GetMapping("/due-date")
  public ResponseEntity<List<BatchStock>> filterStockBySection(@RequestParam(required = true) long section,
      @RequestParam(defaultValue = "0", required = false) Integer numberOfDays) {

    List<BatchStock> batchStocks = batchStockService.filterStockBySection(section, numberOfDays);

    return ResponseEntity.ok().body(batchStocks);
  }
}
