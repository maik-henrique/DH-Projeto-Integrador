package br.com.meli.dhprojetointegrador.service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.enums.DueDateEnum;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.exception.ResourceNotFound;
import br.com.meli.dhprojetointegrador.repository.BatchStockRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BatchStockService {
    private final Clock clock;
    private final BatchStockRepository batchStockRepository;
    private final SectionService sectionService;

    /**
     * Returns a list of batch stocks that has a due date within the allowed range
     * and selected section
     *
     * @param sectionId    id of the section that is the target of the request
     * @param numberOfDays goes to sum this param in current date to filter
     * @return list of batch stock that contains the filters
     * @throws BusinessValidatorException in case section not found
     */

    public List<BatchStock> filterStockBySection(Long sectionId, Integer numberOfDays, Direction ordination) {
        sectionService.findSectionById(sectionId);

        LocalDate dueDate = addDaysInCurrentDate(numberOfDays);

        Sort sort = Sort.by(ordination, "dueDate");

        List<BatchStock> batchStocks = batchStockRepository.findBySectionId(sectionId, dueDate, sort);

        return batchStocks;
    }

    private LocalDate addDaysInCurrentDate(Integer numberOfDays) {
        return LocalDate.now().plusDays(numberOfDays);
    }

    /**
     * @param productId id do produto alvo da requisição
     * @param sortBy    campo base da ordenado do batchStock
     * @return lista de batchStock cuja busca foi bem sucedida
     * @throws ResourceNotFound caso nenhum produto seja encontrado
     * @Author: Maik
     *          Retorna a lista de batch stocks que possuem o produto específicado e
     *          com data de vencimento válida
     */
    public List<BatchStock> findByProductId(Long productId, String sortBy) throws ResourceNotFound {
        Sort sort = Sort.by(sortBy);
        LocalDate minimumDueDate = LocalDate.now(clock);
        LocalDate maxdueDate = minimumDueDate.plusWeeks(DueDateEnum.MAX_DUEDATE_WEEKS.getDuedate());

        List<BatchStock> batchStock = batchStockRepository.findBatchStockByProducts(productId, minimumDueDate,
                maxdueDate, sort);

        if (batchStock.isEmpty()) {
            throw new ResourceNotFound(String.format(
                    "No stock was found for product of id %d or within the maximum due date of %s", productId,
                    maxdueDate.toString()));
        }

        return batchStock;
    }
}
