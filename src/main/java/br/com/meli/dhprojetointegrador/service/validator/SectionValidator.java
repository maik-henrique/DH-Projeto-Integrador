package br.com.meli.dhprojetointegrador.service.validator;

import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.service.SectionService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SectionValidator implements IInboundOrderValidator {
    private SectionService sectionService;
    private Integer sectionId;

    @Override
    public void validate() throws BusinessValidatorException {
        sectionService.findSectionById(sectionId);
    }
}
