package br.com.meli.dhprojetointegrador.dto.request;

import br.com.meli.dhprojetointegrador.entity.Section;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SectionRequestDTO {

    @NotNull
    private String name;

    @NotNull
    private float capacity;

    @NotNull
    private Long warehouseId;

    @NotNull
    private Long categoryId;


    public static Section map(SectionRequestDTO sectionRequestDTO) {
        return Section.builder().name(sectionRequestDTO.getName()).capacity(sectionRequestDTO.getCapacity()).build();
    }
//    public static SectionDto map(Section section) {
//        return SectionDto.builder().
//                name(section.getName()).capacity(section.getCapacity()).build();
//    }


}
