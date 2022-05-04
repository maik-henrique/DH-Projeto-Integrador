package br.com.meli.dhprojetointegrador.dto.response;

import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.Section;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SectionResponseDTO {

    private String name;
    private float capacity;

    public static SectionResponseDTO map(Section section) {
        return SectionResponseDTO.builder().name(section.getName()).capacity(section.getCapacity()).build();
    }

    public static List<SectionResponseDTO> map(List<Section> sections) {
        return sections.stream().map(SectionResponseDTO::map).collect(Collectors.toList());
    }
}
