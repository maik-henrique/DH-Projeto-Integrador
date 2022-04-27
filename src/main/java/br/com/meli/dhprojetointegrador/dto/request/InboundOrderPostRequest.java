package br.com.meli.dhprojetointegrador.dto.request;

import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
  @NotBlank(message = "O campo nome não pode estar em branco")
  @NotNull(message = "O campo nome não pode ser nulo")
  private LocalDate orderDate;

  @NotBlank(message = "O campo nome não pode estar em branco")
  @NotNull(message = "O campo nome não pode ser nulo")
  private Long sectionId;

  @NotBlank(message = "O campo nome não pode estar em branco")
  @NotNull(message = "O campo nome não pode ser nulo")
  private Long agentId;

  @NotBlank(message = "O campo nome não pode estar em branco")
  @NotNull(message = "O campo nome não pode ser nulo")
  private List<@Valid BatchStockPostRequest> batchStock;

}
