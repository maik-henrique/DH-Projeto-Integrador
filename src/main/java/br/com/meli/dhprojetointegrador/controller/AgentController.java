package br.com.meli.dhprojetointegrador.controller;

import br.com.meli.dhprojetointegrador.dto.request.AgentPostRequest;
import br.com.meli.dhprojetointegrador.dto.response.AgentResponseDTO;
import br.com.meli.dhprojetointegrador.entity.Agent;
import br.com.meli.dhprojetointegrador.entity.Warehouse;
import br.com.meli.dhprojetointegrador.service.AgentService;
import br.com.meli.dhprojetointegrador.service.WarehouseService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.Optional;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/fresh-products/admin")
public class AgentController {
    private final AgentService agentService;
    private final ModelMapper modelMapper;

    @PostMapping("/agent")
    public ResponseEntity<?> createAgent(@Valid @RequestBody AgentPostRequest agentPostRequest) {
        try {
            Agent newAgent = modelMapper.map(agentPostRequest, Agent.class);
            Agent agentCreated = agentService.create(newAgent);
            AgentResponseDTO agentResponse = modelMapper.map(agentCreated, AgentResponseDTO.class);
            return new ResponseEntity<>(agentResponse, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    //@GetMapping("/agent")
}
