package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.enums.DueDateEnum;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.repository.BatchStockRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class BatchStockService {

    private final BatchStockRepository batchStockRepository;

    public List<BatchStock> findByProductId(Long productId, String sortBy) {
        Sort sort = Sort.by(sortBy);
        Date maxdueDate = Date.from( Instant.now().plus(DueDateEnum.MAX_DUEDATE_WEEKS.getDuedate(), ChronoUnit.WEEKS));
        List<BatchStock> batchStock = batchStockRepository.findBatchStockByProducts(productId, maxdueDate, sort);

        if (batchStock.isEmpty()) {
            throw new BusinessValidatorException(String.format("No stock was found for product of id %d", productId));
        }
        return batchStock;
    }
}
