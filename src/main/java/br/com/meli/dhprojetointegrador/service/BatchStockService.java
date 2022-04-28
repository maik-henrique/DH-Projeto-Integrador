package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.enums.DueDateEnum;
import br.com.meli.dhprojetointegrador.exception.ResourceNotFound;
import br.com.meli.dhprojetointegrador.repository.BatchStockRepository;
import lombok.AllArgsConstructor;

import org.apache.tomcat.jni.Local;
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
	 *
	 * @Author: Maik
	 * Retorna a lista de batch stocks que possuem o produto específicado e com data de vencimento válida
	 * 
	 * @param productId id do produto alvo da requisição
	 * @param sortBy campo base da ordenado do batchStock
	 * @return lista de batchStock cuja busca foi bem sucedida
	 * @throws ResourceNotFound caso nenhum produto seja encontrado
	 */
	public List<BatchStock> findByProductId(Long productId, String sortBy) throws ResourceNotFound{
		Sort sort = Sort.by(sortBy);
		LocalDate minimumDueDate = LocalDate.now(clock);
		LocalDate maxdueDate = minimumDueDate.plusWeeks(DueDateEnum.MAX_DUEDATE_WEEKS.getDuedate());

		List<BatchStock> batchStock = batchStockRepository.findBatchStockByProducts(productId, minimumDueDate, maxdueDate, sort);

		if (batchStock.isEmpty()) {
			throw new ResourceNotFound(String.format("No stock was found for product of id %d or within the maximum due date of %s", productId, 
					maxdueDate.toString()));
		}
		
		return batchStock;
	}
}
