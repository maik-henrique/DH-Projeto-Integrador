package br.com.meli.dhprojetointegrador.dto.request;

import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InboundOrderPostRequest {

  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  @NotNull(message = "O campo nome não pode ser nulo")
  private LocalDate orderDate;
  
  @NotNull(message = "O campo nome não pode ser nulo")
  private Long sectionId;

  @NotNull(message = "O campo nome não pode ser nulo")
  private Long agentId;

  @NotNull(message = "O campo nome não pode ser nulo")
  private List<@Valid BatchStockPostRequest> batchStock;

}
