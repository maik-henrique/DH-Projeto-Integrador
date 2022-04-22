package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.repository.SectionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;

    public Section findSectionById(Integer id) {
        Optional<Section> sectionOptional = sectionRepository.findById(id);
        if (sectionOptional.isEmpty()) {
            throw new RuntimeException();
        }

        return sectionOptional.get();
    }
}
