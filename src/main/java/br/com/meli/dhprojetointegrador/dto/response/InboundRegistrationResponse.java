package br.com.meli.dhprojetointegrador.dto.response;

import java.util.List;
import br.com.meli.dhprojetointegrador.entity.BatchStock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InboundRegistrationResponse {
  private List<BatchStock> bachStock;

}
