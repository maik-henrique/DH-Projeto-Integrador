package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.entity.Agent;
import br.com.meli.dhprojetointegrador.entity.Warehouse;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.repository.AgentRepository;
import br.com.meli.dhprojetointegrador.repository.WarehouseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AgentService {
    private final AgentRepository agentRepository;
    private final WarehouseRepository warehouseRepository;

    public Agent findAgentById(Long id) throws BusinessValidatorException {
        return agentRepository.findById(id).orElseThrow(() ->
                new BusinessValidatorException(String.format("Agent with id %d not found", id)));
    }

    /**
     * @Author: Micaela Alves
     * @Description: constrói um agente a partir dos dados recebidos e salva no banco de dados
     * @param agent
     * @return agente cadastrado
     */
    public Agent create(Agent agent, Long warehouseId){
        Warehouse warehouse = warehouseRepository.getById(warehouseId);
        Agent newAgent = Agent.builder()
                .name(agent.getName())
                .warehouse(warehouse)
                .build();
        return agentRepository.save(newAgent);
    }

    /**
     * @Author: Micaela Alves
     * @Description: recupera todos os agentes cadastrados no banco de dados
     * @return lista de agentes
     */
    public List<Agent> getAll() {
        return agentRepository.findAll();
    }
}
