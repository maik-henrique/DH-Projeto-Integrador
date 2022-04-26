package br.com.meli.dhprojetointegrador.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionPayloadResponse {
    private int statusCode;
	private String title;	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime timestamp;
	private String description;    
	private Map<String, String> fieldToMessageMap;
}
