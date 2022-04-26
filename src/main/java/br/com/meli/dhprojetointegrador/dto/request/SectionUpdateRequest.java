package br.com.meli.dhprojetointegrador.dto.request;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class SectionUpdateRequest {
    private Long id;
    private String name;
    private float capacity;
}
