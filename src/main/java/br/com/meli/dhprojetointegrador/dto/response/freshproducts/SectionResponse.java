package br.com.meli.dhprojetointegrador.dto.response.freshproducts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SectionResponse {
    private String sectionCode;
    private String warehouseCode;
}
