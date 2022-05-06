package br.com.meli.dhprojetointegrador.controller;

import br.com.meli.dhprojetointegrador.dto.request.AgentPostRequest;
import br.com.meli.dhprojetointegrador.dto.response.AgentCollection;
import br.com.meli.dhprojetointegrador.dto.response.AgentResponseDTO;
import br.com.meli.dhprojetointegrador.entity.Agent;
import br.com.meli.dhprojetointegrador.enums.RoleEnum;
import br.com.meli.dhprojetointegrador.service.AgentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/fresh-products/admin")
public class AgentController {
    private final AgentService agentService;
    private final ModelMapper modelMapper;

    /**
     * @Author: Micaela Alves
     * @Description: cadastra um novo agente
     * @param agentPostRequest
     * @return agente cadastrado
     */
    @PostMapping("/agent")
    public ResponseEntity<?> createAgent(@Valid @RequestBody AgentPostRequest agentPostRequest) {
        try {
            Agent newAgent = modelMapper.map(agentPostRequest, Agent.class);
            Long warehouseId = agentPostRequest.getWarehouseId();
            Agent agentCreated = agentService.create(newAgent,warehouseId);
            AgentResponseDTO agentResponse = modelMapper.map(agentCreated, AgentResponseDTO.class);
            return new ResponseEntity<>(agentResponse, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @Author: Micaela Alves
     * @Description: Consulta todos os agente cadastrados
     * @return lista de agentes
     */

    @GetMapping("/agent")
    public ResponseEntity<?> readAgent() {
        try {
            List<Agent> agents = agentService.getAll();
            return new ResponseEntity<>(AgentCollection.convertToList(agents), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


}
