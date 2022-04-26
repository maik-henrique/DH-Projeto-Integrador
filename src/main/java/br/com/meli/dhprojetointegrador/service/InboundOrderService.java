package br.com.meli.dhprojetointegrador.service;

import java.util.List;

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
    private WarehouseService warehouseService;

    public InboundOrder update(InboundOrder inboundOrder) throws BusinessValidatorException {
        Section section = sectionService.findSectionById(inboundOrder.getSection().getId());
        Agent agent = agentService.findAgentById(inboundOrder.getAgent().getId());
        inboundOrder.getBatchStockList().forEach(batchStock -> {
            Product product = productService.findProductById(batchStock.getProducts().getId());
            batchStock.setProducts(product);
        });

        InboundOrder oldInboundOrder = findInboundOrderByOrderNumber(inboundOrder.getOrderNumber());

        initializeIInboundOrderValidators(section, inboundOrder, agent);
        validators.forEach(IInboundOrderValidator::validate);

        oldInboundOrder.setOrderDate(inboundOrder.getOrderDate());
        oldInboundOrder.setSection(inboundOrder.getSection());
        oldInboundOrder.setAgent(agent);
        oldInboundOrder.setBatchStockList(inboundOrder.getBatchStockList());

        return inboundOrderRepository.save(oldInboundOrder);
    }

    private InboundOrder findInboundOrderByOrderNumber(Integer orderNumber) {
        return inboundOrderRepository
                .findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Recurso nao encontrado"));
    }

    private void initializeIInboundOrderValidators(Section section, InboundOrder inboundOrder, Agent agent) {
        validators = List.of(
                new SectionCategoryValidator(section, inboundOrder),
                new SpaceAvailableValidator(section, inboundOrder),
                new SectionValidator(sectionService, section.getId()),
                new AgentWarehouseValidator(section.getId(), agent.getId(), warehouseService));
    }

    public InboundOrder create(InboundOrder inboundOrder) {
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
