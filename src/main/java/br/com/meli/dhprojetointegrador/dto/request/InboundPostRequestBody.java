package br.com.meli.dhprojetointegrador.dto.request;

import java.util.Date;
import java.util.List;

import br.com.meli.dhprojetointegrador.dto.BatchStockDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InboundPostRequestBody {
  private Date orderDate;
  private Long agentId;
  private Long sectionId;

  private List<BatchStockDTO> batchStock;

}
