package br.com.meli.dhprojetointegrador.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.meli.dhprojetointegrador.entity.Agent;
import br.com.meli.dhprojetointegrador.entity.InboundOrder;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.repository.AgentRepository;
import br.com.meli.dhprojetointegrador.repository.BatchStockRepository;
import br.com.meli.dhprojetointegrador.repository.InboundOrderRepository;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import br.com.meli.dhprojetointegrador.repository.SectionRepository;
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
    private WarehouseService warehouseService;

    private final SectionRepository sectionRepository;
    private final AgentRepository agentRepository;
    private final BatchStockRepository batchStockRepository;
    private final ProductRepository productRepository;

    /**
     * Given an InboundOrder request it updates it's related fields if they do
     * exist.
     * 
     * @param inboundOrder an instance of InboundOrder to be updated
     * @return instance of InboundOrder updated
     * @throws BusinessValidatorException in case it fails to update the
     *                                    InboundOrder properly
     */
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

    private InboundOrder findInboundOrderByOrderNumber(Long orderNumber) {
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

    public InboundOrder create(InboundOrder inboundOrder) {
        Section section = sectionService.findSectionById(inboundOrder.getSection().getId());
        Agent agent = agentService.findAgentById(inboundOrder.getAgent().getId());

        inboundOrder.getBatchStockList().forEach(batchStock -> {
            Product product = productService.findProductById(batchStock.getProducts().getId());
            batchStock.setProducts(product);
        });

        initializeIInboundOrderValidators(section, inboundOrder, agent);
        System.out.println("OBA");
        validators.forEach(IInboundOrderValidator::validate);

        inboundOrder.setAgent(agent);
        inboundOrder.setSection(section);

        return inboundOrderRepository.save(inboundOrder);

    }

}
