package br.com.meli.dhprojetointegrador.unit.service;



import br.com.meli.dhprojetointegrador.entity.Agent;
import br.com.meli.dhprojetointegrador.repository.AgentRepository;
import br.com.meli.dhprojetointegrador.repository.WarehouseRepository;
import br.com.meli.dhprojetointegrador.service.AgentService;
import br.com.meli.dhprojetointegrador.unit.util.AgentCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AgentServiceTests {

    @InjectMocks
    private AgentService agentService;

    AgentRepository agentRepository = mock(AgentRepository.class);
    WarehouseRepository warehouseRepository = mock(WarehouseRepository.class);

    Agent agent1 = AgentCreator.createValidAgent();
    Agent agent2 = AgentCreator.createValidAgent();


    List<Agent> agentList = Arrays.asList(agent1, agent2);


    @Test
    @DisplayName("Test Req-6 - getAllAgents")
    public void returns_list_agents_when_successful(){
        when(agentRepository.findAll()).thenReturn(agentList);
        List<Agent> result = agentService.getAll();

        assert result.equals(agentList);
        assert result.get(0).getName().equals("Agente");
    }

    @Test
    @DisplayName("Test Req-6 - createAgent")
    public void return_agent_when_successful_created(){
        when(agentRepository.save(ArgumentMatchers.any(Agent.class))).thenReturn(agent1);
        Agent result = agentService.create(agent1, 1L);
        assert result.equals(agent1);
        assert result.getName().equals("Agente");

    }

    @Test
    @DisplayName("Test - findById")
    public void return_agent_by_id_when_successful(){
        when(agentRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(agent1));
        Agent result = agentService.findAgentById(1L);
        assert result.equals(agent1);
    }
}
