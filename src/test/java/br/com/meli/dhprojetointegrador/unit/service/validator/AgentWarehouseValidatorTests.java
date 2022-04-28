package br.com.meli.dhprojetointegrador.unit.service.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import br.com.meli.dhprojetointegrador.entity.Agent;
import br.com.meli.dhprojetointegrador.entity.Category;
import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.entity.Warehouse;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.repository.AgentRepository;
import br.com.meli.dhprojetointegrador.repository.SectionRepository;
import br.com.meli.dhprojetointegrador.repository.WarehouseRepository;
import br.com.meli.dhprojetointegrador.service.WarehouseService;
import br.com.meli.dhprojetointegrador.service.validator.AgentWarehouseValidator;
import br.com.meli.dhprojetointegrador.unit.util.SectionCreator;
import br.com.meli.dhprojetointegrador.unit.util.WarehouseCreator;

@ExtendWith(SpringExtension.class)
public class AgentWarehouseValidatorTests {

        private AgentWarehouseValidator agentWarehouseValidator;

        @Mock
        private WarehouseRepository warehouseRepository;
        @Mock
        private SectionRepository sectionRepository;

        @Mock
        private AgentRepository agentRepository;

        @BeforeEach
        void setUp() {
                BDDMockito.when(warehouseRepository.save(ArgumentMatchers.any(Warehouse.class)))
                                .thenReturn(WarehouseCreator.createValidWarehouse());

                BDDMockito.when(sectionRepository.save(ArgumentMatchers.any(Section.class)))
                                .thenReturn(SectionCreator.createValidSection());

                BDDMockito.when(sectionRepository.findById(ArgumentMatchers.anyLong()))
                                .thenReturn(Optional.of(SectionCreator.createValidSection()));

                BDDMockito.when(warehouseRepository.findBySections(ArgumentMatchers.any(Section.class)))
                                .thenReturn(Optional.of(WarehouseCreator.createValidWarehouse()));

                BDDMockito.when(agentRepository.save(ArgumentMatchers.any(Agent.class)))
                                .thenReturn(Agent.builder().build());
        }

        /**
         * @Author: Bruno
         * @Teste: Teste unitário da validação de agente pertence a armazém
         * @Description: valida o funcionamento correto da função
         */
        @Test
        public void validate_shouldNotThrowException_whenAgentIdIsEqualWarehouseId() {
                WarehouseService warehouseService = new WarehouseService(warehouseRepository);

                Category frios = Category.builder().name(CategoryEnum.FRIOS).build();

                Warehouse warehouse = warehouseRepository.save(Warehouse.builder().name("Warehouse 1").build());

                Section section = Section.builder().category(frios).name("Section 2")
                                .warehouse(warehouse).capacity(10F).id(1L).build();

                Agent agent = Agent.builder().name("Test").id(warehouse.getId()).build();

                sectionRepository.save(section);
                agentRepository.save(agent);

                agentWarehouseValidator = new AgentWarehouseValidator(section, agent.getId(), warehouseService);

                assertDoesNotThrow(() -> agentWarehouseValidator.validate());
        }

        /**
         * @Author: Bruno
         * @Teste: Teste unitário da validação de agente pertence a armazém
         * @Description: valida o funcionamento incorreto da função
         */
        @Test
        public void validate_shouldThrowException_whenAgentIdIsNotEqualWarehouseId() {
                WarehouseService warehouseService = new WarehouseService(warehouseRepository);

                Section section = Section.builder().name("Section 2").id(1L).build();

                Agent agent = Agent.builder().name("Test").id(2L).build();

                sectionRepository.save(section);
                agentRepository.save(agent);

                agentWarehouseValidator = new AgentWarehouseValidator(section, agent.getId(), warehouseService);

                assertThrows(BusinessValidatorException.class, () -> agentWarehouseValidator.validate());
        }

}
