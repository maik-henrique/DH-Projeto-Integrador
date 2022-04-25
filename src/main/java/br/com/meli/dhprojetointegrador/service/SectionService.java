package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.repository.SectionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;

    public Section findSectionById(Integer id) {
        return sectionRepository.findById(id)
                .orElseThrow(() -> new BusinessValidatorException(String.format("Section of id %d not found", id)));
    }
}
