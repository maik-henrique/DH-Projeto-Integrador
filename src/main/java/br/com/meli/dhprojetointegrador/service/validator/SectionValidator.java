package br.com.meli.dhprojetointegrador.service.validator;

import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.service.SectionService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SectionValidator implements IInboundOrderValidator {
    private SectionService sectionService;
    private Long sectionId;

    /**
     * Author: Pedro Dalpa
     * Method: validate
     * Description: valida se uma seção existe, caso nao exista retornara um
     * BusinessValidatorException
     * 
     * 
     */

    @Override
    public void validate() throws BusinessValidatorException {
        sectionService.findSectionById(sectionId);
    }
}
