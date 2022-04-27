package br.com.meli.dhprojetointegrador.dto.request;

import java.util.Date;
import java.util.List;
import java.util.Set;
import br.com.meli.dhprojetointegrador.dto.BatchStockDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InboundPostRequestBody {

  @NotNull(message = "O campo nome não pode ser nulo")
  @NotBlank(message = "O campo nome não pode estar em branco")
  @DateTimeFormat
  private Date orderDate;

  @NotBlank(message = "O campo nome não pode estar em branco")
  @NumberFormat
  @NotNull(message = "O campo nome não pode ser nulo")
  private Long agentId;

  @NotBlank(message = "O campo nome não pode estar em branco")
  @NumberFormat
  @NotNull(message = "O campo nome não pode ser nulo")
  private Long sectionId;

  @NotBlank(message = "O campo nome não pode estar em branco")
  @NotNull(message = "O campo nome não pode ser nulo")
  private Set<BatchStockDTO> batchStock;


}
