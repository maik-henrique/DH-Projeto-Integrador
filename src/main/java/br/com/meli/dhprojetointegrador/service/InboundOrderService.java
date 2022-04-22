package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.entity.InboundOrder;
import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.repository.InboundOrderRepository;
import br.com.meli.dhprojetointegrador.service.validator.IInboundOrderValidator;
import br.com.meli.dhprojetointegrador.service.validator.SectionCategoryValidator;
import br.com.meli.dhprojetointegrador.service.validator.SpaceAvailableValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InboundOrderService {
    private final InboundOrderRepository inboundOrderRepository;
    private final SectionService sectionService;
    private List<IInboundOrderValidator> validators;

    public void update(InboundOrder inboundOrder) {
        Section section = sectionService.findSectionById(inboundOrder.getSection().getId());

        InboundOrder oldInboundOrder = inboundOrderRepository.findByOrderNumber(inboundOrder.getOrderNumber())
                .orElseThrow(() -> new RuntimeException("Recurso nao encontrado"));

        initializeIInboundOrderValidators(section, inboundOrder);
        validators.forEach(IInboundOrderValidator::validate);

        inboundOrder.setOrderDate(inboundOrder.getOrderDate());
        
        inboundOrderRepository.save(inboundOrder);
    }

    private void initializeIInboundOrderValidators(Section section, InboundOrder inboundOrder) {
        validators = List.of(
                new SectionCategoryValidator(section, inboundOrder),
                new SpaceAvailableValidator(section, inboundOrder)
        );
    }
}
