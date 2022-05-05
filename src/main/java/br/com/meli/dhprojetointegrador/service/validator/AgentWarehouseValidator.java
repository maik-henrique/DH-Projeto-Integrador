package br.com.meli.dhprojetointegrador.service.validator;

import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.entity.Warehouse;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.service.WarehouseService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AgentWarehouseValidator implements IInboundOrderValidator {

    private Section section;
    private Long agentId;
    private WarehouseService warehouseService;

    /**
     * Author: Pedro Dalpa
     * Method: validate
     * Description: valida se o id do agente que foi passado esta ligado a aquele
     * armazém
     * 
     */

    @Override
    public void validate() {
        Warehouse warehouse = warehouseService.findWarehouseIdBySection(section);

        if (agentId != warehouse.getId()) {
            throw new BusinessValidatorException("Agent id not valid");
        }
    }
}
