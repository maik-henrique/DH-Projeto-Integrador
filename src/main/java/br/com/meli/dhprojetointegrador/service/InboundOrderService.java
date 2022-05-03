package br.com.meli.dhprojetointegrador.service;

import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import br.com.meli.dhprojetointegrador.entity.Agent;
import br.com.meli.dhprojetointegrador.entity.InboundOrder;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.repository.InboundOrderRepository;
import br.com.meli.dhprojetointegrador.service.validator.AgentWarehouseValidator;
import br.com.meli.dhprojetointegrador.service.validator.IInboundOrderValidator;
import br.com.meli.dhprojetointegrador.service.validator.SectionCategoryValidator;
import br.com.meli.dhprojetointegrador.service.validator.SectionValidator;
import br.com.meli.dhprojetointegrador.service.validator.SpaceAvailableValidator;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InboundOrderService {
    private final InboundOrderRepository inboundOrderRepository;
    private final SectionService sectionService;
    private final AgentService agentService;
    private final ProductService productService;
    private List<IInboundOrderValidator> validators;
    private final WarehouseService warehouseService;

    /**
     * @Author: Maik
     * Dado uma InboundOrder atualiza todos os seus campos
     * 
     * @param inboundOrder instância que será atualizada
     * @return instância atualizada de InboundOrder
     * @throws BusinessValidatorException Caso não consiga finalizar a atualização
     */
    //@CachePut(value = "update", key = "#inboundOrder")
    public InboundOrder update(InboundOrder inboundOrder) throws BusinessValidatorException {

        Section section = sectionService.findSectionById(inboundOrder.getSection().getId());
        Agent agent = agentService.findAgentById(inboundOrder.getAgent().getId());
        InboundOrder oldInboundOrder = findInboundOrderByOrderNumber(inboundOrder.getOrderNumber());

        inboundOrder.getBatchStockList().forEach(batchStock -> {
            Product product = productService.findProductById(batchStock.getProducts().getId());
            batchStock.setProducts(product);
            batchStock.setInboundOrder(oldInboundOrder);
        });

        initializeIInboundOrderValidators(section, inboundOrder, agent);
        validators.forEach(IInboundOrderValidator::validate);

        oldInboundOrder.setOrderDate(inboundOrder.getOrderDate());
        oldInboundOrder.setSection(section);
        oldInboundOrder.setAgent(agent);
        oldInboundOrder.setBatchStockList(inboundOrder.getBatchStockList());

        return inboundOrderRepository.save(oldInboundOrder);
    }

    private InboundOrder findInboundOrderByOrderNumber(Long orderNumber) throws BusinessValidatorException{
        return inboundOrderRepository
                .findByOrderNumber(orderNumber)
                .orElseThrow(
                        () -> new BusinessValidatorException("Inbound order not found with the provider order number"));
    }

    private void initializeIInboundOrderValidators(Section section, InboundOrder inboundOrder, Agent agent) {
        validators = List.of(
                new SectionCategoryValidator(section, inboundOrder),
                new SpaceAvailableValidator(section, inboundOrder),
                new SectionValidator(sectionService, section.getId()),
                new AgentWarehouseValidator(section, agent.getId(), warehouseService));
    }

    /**
     * Author: Pedro Dalpa
     * Method: create
     * Description: salva o inbound order e cria os registros no stock conforme
     * necessário
     * 
     * @param inboundOrder an instance of InboundOrder to create
     * @return instance of InboundOrder created
     * @throws BusinessValidatorException in case it fails to created the
     *                                    InboundOrder properly
     */
    //@CacheEvict(value = "create", key = "#inboundOrder")
    public InboundOrder create(InboundOrder inboundOrder) throws BusinessValidatorException {
        Section section = sectionService.findSectionById(inboundOrder.getSection().getId());
        Agent agent = agentService.findAgentById(inboundOrder.getAgent().getId());

        inboundOrder.getBatchStockList().forEach(batchStock -> {
            Product product = productService.findProductById(batchStock.getProducts().getId());
            batchStock.setProducts(product);
        });

        initializeIInboundOrderValidators(section, inboundOrder, agent);
        validators.forEach(IInboundOrderValidator::validate);

        inboundOrder.setAgent(agent);
        inboundOrder.setSection(section);

        return inboundOrderRepository.save(inboundOrder);

    }

}
