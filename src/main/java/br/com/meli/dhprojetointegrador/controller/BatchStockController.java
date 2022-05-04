package br.com.meli.dhprojetointegrador.controller;

import java.util.List;

import br.com.meli.dhprojetointegrador.dto.BatchStockDTO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
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
   * Author: Mariana Galdino
   * Method: filterStockBySection
   * Description: Busca estoque filtrando pelo parâmetros
   * 
   * @param sectionId    id da seção para filtrar
   * @param numberOfDays adicionar quantidade de dias a data atual
   * @param ordination   ordenar por dada de validade (ASC ou DESC)
   * @param category     filtrar por categoria (por padrão traz todas)
   * @return lista os batch estoque utilizando os filtros
   * @throws BusinessValidatorException se a seção nao for encontrada
   *
   */
  @GetMapping("/due-date")
  public ResponseEntity<List<BatchStockDTO>> filterStockBySection(@RequestParam(required = true) long sectionId,
                                                                  @RequestParam(defaultValue = "0", required = false) Integer numberOfDays,
                                                                  @RequestParam(defaultValue = "ASC", required = false) Direction ordination,
                                                                  @RequestParam(defaultValue = "FF, RF, FS", required = false) List<CategoryEnum> category) {

    List<BatchStock> batchStocks = batchStockService.filterStockBySection(
        sectionId,
        numberOfDays,
        ordination,
        category);

    return ResponseEntity.ok().body(BatchStockDTO.map(batchStocks));


  }
}
