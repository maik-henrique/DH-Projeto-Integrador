package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.repository.BatchStockRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BatchStockService {

    private final BatchStockRepository batchStockRepository;

    public List<BatchStock> findByProductId(Long productId, String sortBy) {
        Sort sort = Sort.by(sortBy);
        return batchStockRepository.findBatchStockByProducts(productId, sort);
    }
}
