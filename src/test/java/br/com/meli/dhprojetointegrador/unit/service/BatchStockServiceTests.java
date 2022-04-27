package br.com.meli.dhprojetointegrador.unit.service;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.repository.BatchStockRepository;
import br.com.meli.dhprojetointegrador.service.BatchStockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BatchStockServiceTests {

    @InjectMocks
    private BatchStockService batchStockService;

    @Mock
    private BatchStockRepository batchStockRepository;

    @Test
    public void findByProductId_shouldReturnListOfProducts_whenProductsAreFound() {
        when(batchStockRepository.findBatchStockByProducts(anyLong(), any(Sort.class)))
                .thenReturn(List.of(BatchStock.builder().build()));

        List<BatchStock> batchStock = batchStockService.findByProductId(1L, "batchNumber");

        assertNotNull(batchStock);
        assertFalse(batchStock.isEmpty());
        verify(batchStockRepository, times(1)).findBatchStockByProducts(1L, Sort.by("batchNumber"));
    }

    @Test
    public void findByProductId_shouldThrowBusinessValidatorException_whenProductsAreNotFound() {
        when(batchStockRepository.findBatchStockByProducts(anyLong(), any(Sort.class)))
                .thenReturn(List.of());

        assertThrows(BusinessValidatorException.class, () -> batchStockService.findByProductId(1L, "batchNumber"));
    }
}
