package br.com.meli.dhprojetointegrador.service.validator;

import br.com.meli.dhprojetointegrador.entity.Warehouse;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.service.WarehouseService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AgentWarehouseValidator implements IInboundOrderValidator {

    private Integer sectionId;
    private Integer agentId;
    private WarehouseService warehouseService;

    @Override
    public void validate() {
        Warehouse warehouse = warehouseService.findWarehouseIdBySectionId(sectionId);

        if (agentId != warehouse.getId()) {
            throw new BusinessValidatorException("Agent id not valid");
        }
    }
}
