package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.entity.Agent;
import br.com.meli.dhprojetointegrador.entity.Warehouse;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.exception.ResourceNotFound;
import br.com.meli.dhprojetointegrador.repository.AgentRepository;
import br.com.meli.dhprojetointegrador.repository.WarehouseRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AgentService {
    private final AgentRepository agentRepository;
    private final WarehouseRepository warehouseRepository;

    //@Cacheable(value = "findAgentById", key = "#id")
    public Agent findAgentById(Long id) throws BusinessValidatorException {
        return agentRepository.findById(id).orElseThrow(() ->
                new BusinessValidatorException(String.format("Agent with id %d not found", id)));
    }

    public Agent create(Agent agent){
        Warehouse warehouse = warehouseRepository.getById(agent.getWarehouse().getId());
        System.out.println(warehouse);
        Agent newAgent = Agent.builder()
                .name(agent.getName())
                .password(agent.getPassword())
                .warehouse(warehouse)
                .build();
        return agentRepository.save(newAgent);
    }

    public List<Agent> getAll() {
        return agentRepository.findAll();
    }
}
