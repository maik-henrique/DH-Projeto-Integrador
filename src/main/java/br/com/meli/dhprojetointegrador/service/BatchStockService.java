package br.com.meli.dhprojetointegrador.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.repository.BatchStockRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BatchStockService {
  private BatchStockRepository batchStockRepository;
  private SectionService sectionService;

  public List<BatchStock> filterStockBySection(Long sectionId, Integer numberOfDays) {
    sectionService.findSectionById(sectionId);

    LocalDate dueDate = addDaysInCurrentDate(numberOfDays);

    List<BatchStock> batchStocks = batchStockRepository.findBySectionId(sectionId, dueDate);

    return batchStocks;
  }

  private LocalDate addDaysInCurrentDate(Integer numberOfDays) {
    return LocalDate.now().plusDays(numberOfDays);
  }

}