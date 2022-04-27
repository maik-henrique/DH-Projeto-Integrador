package br.com.meli.dhprojetointegrador.unit.service;

import br.com.meli.dhprojetointegrador.entity.*;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.repository.InboundOrderRepository;
import br.com.meli.dhprojetointegrador.service.AgentService;
import br.com.meli.dhprojetointegrador.service.InboundOrderService;
import br.com.meli.dhprojetointegrador.service.ProductService;
import br.com.meli.dhprojetointegrador.service.SectionService;
import br.com.meli.dhprojetointegrador.service.validator.IInboundOrderValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InboundOrderServiceTests {

    @InjectMocks
    private InboundOrderService inboundOrderService;

    @Mock
    private InboundOrderRepository inboundOrderRepository;

    @Mock
    private SectionService sectionService;

    @Mock
    private AgentService agentService;

    @Mock
    private ProductService productService;

    @Mock
    private List<IInboundOrderValidator> validators;

    @Test
    public void update_shouldProperlyCallSaveWithUpdatedObject_whenAllNestedObjectsAreRetrievedProperly() {
        Set<BatchStock> batchStockList = Set.of();
        Section section = Section.builder().id(1L).category(Category.builder().name(CategoryEnum.FRIOS).build()).build();
        Agent agent = Agent.builder().id(1L).build();

        when(sectionService.findSectionById(anyLong())).thenReturn(section);
        when(agentService.findAgentById(anyLong())).thenReturn(Agent.builder().build());

        InboundOrder oldInboundOrder = InboundOrder.builder().orderNumber(12L).build();
        when(inboundOrderRepository.findByOrderNumber(anyLong())).thenReturn(Optional.of(oldInboundOrder));

        InboundOrder inboundOrderRequest = InboundOrder.builder()
                .orderNumber(123L)
                .section(section)
                .agent(agent)
                .batchStockList(batchStockList)
                .build();

        inboundOrderService.update(inboundOrderRequest);

        verify(inboundOrderRepository, times(1)).save(any());
    }

}
