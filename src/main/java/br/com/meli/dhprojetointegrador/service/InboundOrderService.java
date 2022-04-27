package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.dto.request.InboundPostRequestBody;
import br.com.meli.dhprojetointegrador.entity.*;
import br.com.meli.dhprojetointegrador.exception.BadRequestException;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.mapper.BatchStockMapper;
import br.com.meli.dhprojetointegrador.repository.*;
import br.com.meli.dhprojetointegrador.service.validator.IInboundOrderValidator;
import br.com.meli.dhprojetointegrador.service.validator.SectionCategoryValidator;
import br.com.meli.dhprojetointegrador.service.validator.SpaceAvailableValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@AllArgsConstructor
public class InboundOrderService {
    private final InboundOrderRepository inboundOrderRepository;
    private final SectionService sectionService;
    private final AgentService agentService;
    private final ProductService productService;
    private List<IInboundOrderValidator> validators;

    private final SectionRepository sectionRepository;
    private final AgentRepository agentRepository;
    private final BatchStockRepository batchStockRepository;
    private final ProductRepository productRepository;

    /**
     * Given an InboundOrder request it updates it's related fields if they do exist.
     * 
     * @param inboundOrder an instance of InboundOrder to be updated
     * @return instance of InboundOrder updated
     * @throws BusinessValidatorException in case it fails to update the InboundOrder properly
     */
    public InboundOrder update(InboundOrder inboundOrder) throws BusinessValidatorException {
        Section section = sectionService.findSectionById(inboundOrder.getSection().getId());
        Agent agent = agentService.findAgentById(inboundOrder.getAgent().getId());
        inboundOrder.getBatchStockList().forEach(batchStock -> {

            Product product = productService.findProductById(batchStock.getProducts().getId());
            batchStock.setProducts(product);
        });

        InboundOrder oldInboundOrder = findInboundOrderByOrderNumber(inboundOrder.getOrderNumber());

        initializeIInboundOrderValidators(section, inboundOrder);
        validators.forEach(IInboundOrderValidator::validate);

        oldInboundOrder.setOrderDate(inboundOrder.getOrderDate());
        oldInboundOrder.setSection(inboundOrder.getSection());
        oldInboundOrder.setAgent(agent);
        oldInboundOrder.setBatchStockList(inboundOrder.getBatchStockList());

        return inboundOrderRepository.save(oldInboundOrder);
    }

    private InboundOrder findInboundOrderByOrderNumber(Long orderNumber) {
        return inboundOrderRepository
                .findByOrderNumber(orderNumber)
                .orElseThrow(() -> new BusinessValidatorException("Inbound order not found with the provider order number"));
    }

    private void initializeIInboundOrderValidators(Section section, InboundOrder inboundOrder) {
        validators = List.of(
                new SectionCategoryValidator(section, inboundOrder),
                new SpaceAvailableValidator(section, inboundOrder)
        );
    }

  public InboundOrder create(InboundPostRequestBody inboundPostRequestBody) {
    InboundOrder inboundOrder = new InboundOrder();

    Section section = sectionRepository.findById(inboundPostRequestBody.getSectionId())
        .orElseThrow(() -> new BadRequestException("Section id not found"));

    Agent agent = agentRepository.findById(inboundPostRequestBody.getAgentId())
        .orElseThrow(() -> new BadRequestException("Agent id not found"));

    LocalDate convertedDate = inboundPostRequestBody.getOrderDate().toInstant().atZone(ZoneId.systemDefault())
        .toLocalDate();

    inboundOrder.setSection(section);
    inboundOrder.setOrderDate(convertedDate);
    inboundOrder.setAgent(agent);

    InboundOrder savedInboundOrder = inboundOrderRepository.save(inboundOrder);

    inboundPostRequestBody.getBatchStock().forEach(item -> {
      item.setInboundOrder(savedInboundOrder);
      Product product = productRepository.findById(item.getProduct_id())
          .orElseThrow(() -> new BadRequestException("Agent id not found"));
      item.setProducts(product);
    });

    List<BatchStock> batchStocks = BatchStockMapper.INSTANCE.toBatchStock(inboundPostRequestBody.getBatchStock());

    batchStockRepository.saveAll(batchStocks);

    return savedInboundOrder;
  }
}
