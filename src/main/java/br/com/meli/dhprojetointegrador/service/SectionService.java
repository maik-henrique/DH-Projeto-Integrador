package br.com.meli.dhprojetointegrador.service;

import org.springframework.stereotype.Service;

import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.repository.SectionRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;

    public Section findSectionById(Long id) {
        return sectionRepository.findById(id)
                .orElseThrow(() -> new BusinessValidatorException(String.format("Section of id %d not found", id)));
    }
}
