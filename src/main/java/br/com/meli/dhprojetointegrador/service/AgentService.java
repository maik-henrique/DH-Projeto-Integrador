package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.entity.Agent;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.repository.AgentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AgentService {
    private final AgentRepository agentRepository;

    //@Cacheable(value = "findAgentById", key = "#id")
    public Agent findAgentById(Long id) throws BusinessValidatorException {
        return agentRepository.findById(id).orElseThrow(() ->
                new BusinessValidatorException(String.format("Agent with id %d not found", id)));
    }

}
