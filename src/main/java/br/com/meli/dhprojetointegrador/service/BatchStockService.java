package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.enums.DueDateEnum;
import br.com.meli.dhprojetointegrador.exception.ResourceNotFound;
import br.com.meli.dhprojetointegrador.repository.BatchStockRepository;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class BatchStockService {

	private final BatchStockRepository batchStockRepository;
	private final Clock clock;

	/**
	 * Returns a list of batch stocks that has a due date within the allowed range
	 * 
	 * @param productId id of the product that is the target of the request
	 * @param sortBy field name that will be used to sort the batch stock list
	 * @return list of batch stock that contains the target product 
	 * @throws ResourceNotFound in case no match is found
	 */
	public List<BatchStock> findByProductId(Long productId, String sortBy) throws ResourceNotFound{
		Sort sort = Sort.by(sortBy);

		LocalDate maxdueDate = LocalDate.now(clock).plusWeeks(DueDateEnum.MAX_DUEDATE_WEEKS.getDuedate());
		List<BatchStock> batchStock = batchStockRepository.findBatchStockByProducts(productId, maxdueDate, sort);

		if (batchStock.isEmpty()) {
			throw new ResourceNotFound(String.format("No stock was found for product of id %d or within the maximum due date of %s", productId, 
					maxdueDate.toString()));
		}
		
		return batchStock;
	}
}
