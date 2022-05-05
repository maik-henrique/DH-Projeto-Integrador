package br.com.meli.dhprojetointegrador.unit.service;


import br.com.meli.dhprojetointegrador.entity.Agent;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.repository.AgentRepository;
import br.com.meli.dhprojetointegrador.service.AgentService;
import br.com.meli.dhprojetointegrador.unit.util.AgentCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AgentServiceTests {

    @InjectMocks
    private AgentService agentService;

    AgentRepository repository = mock(AgentRepository.class);

    Agent agent1 = AgentCreator.createValidAgent();
    Agent agent2 = AgentCreator.createValidAgent();
    List<Agent> agentList = Arrays.asList(agent1, agent2);


    @Test
    @DisplayName("Test Req-6 - getAllAgents")
    public void returnAgents_should_return_correct_list(){
        when(repository.findAll()).thenReturn(agentList);
        List<Agent> result = agentService.getAll();
        System.out.println(agentList);

        assert result.equals(agentList);
        assert result.get(0).getName().equals("Agente");

    }
}
