package br.com.meli.dhprojetointegrador.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionPayloadResponse {

    private int statusCode;
    private String title;
    private LocalDateTime timestamp;
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Map<String, String> fieldToMessageMap;
}
