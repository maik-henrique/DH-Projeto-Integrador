package br.com.meli.dhprojetointegrador.controller;

import br.com.meli.dhprojetointegrador.dto.request.SectionRequestDTO;
import br.com.meli.dhprojetointegrador.dto.request.SectionUpdateRequest;
import br.com.meli.dhprojetointegrador.dto.response.SectionResponseDTO;
import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.repository.SectionRepository;
import br.com.meli.dhprojetointegrador.service.SectionService;
import lombok.AllArgsConstructor;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@ResponseBody
@RequestMapping(SectionController.baseUri)
public class SectionController {

    public static final String baseUri = "/api/v1/fresh-products";

    private SectionService sectionService;
    private SectionRepository sectionRepository;

    /**
     * Author: Mariana Galdino
     * Method: Buscar todas as sections pelo id da warehouse
     * Description: Serviço responsavel por retornar todas as secoes de acordo com o id da warehouse
     * @return lista de sections
     */
    @GetMapping("/sections")
    public ResponseEntity<List<SectionResponseDTO>> returnAllSections(@RequestParam( required = true) long warehouseId) {
        List<Section> sections = sectionService.returnAllSectionsByWarehouse(warehouseId);
        return ResponseEntity.ok(SectionResponseDTO.map(sections));

    }

    /**
     * Author: Mariana Galdino
     * Method: Buscar todas as sections pelo id da categoria
     * Description: Serviço responsavel por retornar todas as secoes de acordo com o id da categoria
     * @return lista de sections
     */
    @GetMapping("/sections-category")
    public ResponseEntity<List<SectionResponseDTO>> returnAllSectionsByCategory(@RequestParam(required = true) long categoryId) {
        List<Section> sections = sectionService.returnAllSectionsByCategory(categoryId);
        return ResponseEntity.ok(SectionResponseDTO.map(sections));

    }
    /**
     * Author: Mariana Galdino
     * Method: Registra uma section em uma categoria e warehouse
     * Description: Serviço responsavel por cadastrar uma secao com uma categoria e warehouse
     * @return secao cadastrada e status
     */
    @PostMapping("/register-section")
    public ResponseEntity<?> create(@RequestBody SectionRequestDTO sectionRequestDTO, UriComponentsBuilder uriBuilder) {

        Section section = SectionRequestDTO.map(sectionRequestDTO);
        sectionService.create(section, sectionRequestDTO.getWarehouseId(), sectionRequestDTO.getCategoryId());
        SectionResponseDTO dto = SectionResponseDTO.map(section);


            URI uri = uriBuilder
                    .path(SectionController.baseUri.concat("/{i}"))
                    .buildAndExpand(section.getId())
                    .toUri();

            return ResponseEntity.created(uri).body(dto);
    }

    /**
     * Author: Mariana Galdino
     * Method: altera a capacidade de uma section
     * Description: Serviço responsavel por alterar a capacidade de uma secao
     * @return secao alterada e status
     */

    @PatchMapping("/update-section")
    public ResponseEntity<?> updateSectionCapacity(@RequestBody SectionUpdateRequest sectionUpdateRequest){
        Section section = sectionService.findSectionById(sectionUpdateRequest.getId());
        section.setCapacity(sectionUpdateRequest.getCapacity());
        sectionRepository.save(section);

        return ResponseEntity.ok(sectionUpdateRequest);

    }

}






