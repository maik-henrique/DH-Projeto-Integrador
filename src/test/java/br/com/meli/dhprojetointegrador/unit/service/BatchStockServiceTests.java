package br.com.meli.dhprojetointegrador.unit.service;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.repository.BatchStockRepository;
import br.com.meli.dhprojetointegrador.service.BatchStockService;
import br.com.meli.dhprojetointegrador.service.SectionService;
import br.com.meli.dhprojetointegrador.unit.util.BatchStockCreator;
import br.com.meli.dhprojetointegrador.unit.util.SectionCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BatchStockServiceTests {

    private final static LocalDate LOCAL_DATE_NOW_MOCK = LocalDate.of(2022, 4, 13);
    @InjectMocks
    private BatchStockService batchStockService;
    @Mock
    private BatchStockRepository batchStockRepository;
    @Mock
    private Clock clock;
    @Mock
    private SectionService sectionService;

    @BeforeEach
    public void setup() {
        lenient().when(clock.instant()).thenReturn(LOCAL_DATE_NOW_MOCK.atStartOfDay(ZoneId.systemDefault()).toInstant());
        lenient().when(clock.getZone()).thenReturn(ZoneId.of("Z"));
    }

    @Test
    @DisplayName(value = "Filtro de produtos - Produto encontrado")
    public void findByProductId_shouldReturnListOfProducts_whenProductsAreFound() {
        when(batchStockRepository.findBatchStockByProducts(anyLong(), any(LocalDate.class), any(Sort.class)))
                .thenReturn(List.of(BatchStock.builder().build()));

        List<BatchStock> batchStock = batchStockService.findByProductId(1L, "batchNumber");

        assertNotNull(batchStock);
        assertFalse(batchStock.isEmpty());
        verify(batchStockRepository, times(1)).findBatchStockByProducts(anyLong(), any(LocalDate.class),
                any(Sort.class));
    }

    @Test
    @DisplayName(value = "Filtro de produtos - Produto não encontrado")
    public void findByProductId_shouldThrowBusinessValidatorException_whenProductsAreNotFound() {
        when(batchStockRepository.findBatchStockByProducts(anyLong(), any(LocalDate.class), any(Sort.class)))
                .thenReturn(List.of());
        assertThrows(BusinessValidatorException.class, () -> batchStockService.findByProductId(1L, "batchNumber"));
    }

    /**
     * Author: Pedro Dalpa
     * Author: Mariana Galdino
     * Method: filterStockBySection
     * Description: Busca estoque filtrando pelo parâmetros
     *
     * @param sectionId    id da seção para filtrar
     * @param numberOfDays adicionar quantidade de dias a data atual
     * @param ordination   ordenar por dada de validade (ASC ou DESC)
     * @return lista os batch estoque utilizando os filtros
     *
     */
    @Test
    @DisplayName("Retorna os BatchStocks de acordo com os parametros")
    public void shouldReturnAllBatchStockFoundBySectionId(){


        when(batchStockRepository.findBySectionId(anyLong(), any(LocalDate.class), anyList(), any(Sort.class)))
                .thenReturn(List.of(BatchStockCreator.batchStock()));
        when(sectionService.findSectionById(anyLong())).thenReturn(SectionCreator.createValidSection());

        List<CategoryEnum> category = new ArrayList<>();
        category.add(CategoryEnum.FF);

        List<BatchStock> batchStocks = batchStockService.filterStockBySection(1L, 5, Sort.Direction.ASC, category );

        assertFalse(batchStocks.isEmpty());
    }

}
