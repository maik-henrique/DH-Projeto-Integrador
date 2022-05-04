package br.com.meli.dhprojetointegrador.dto.response;

import br.com.meli.dhprojetointegrador.entity.Agent;
import br.com.meli.dhprojetointegrador.entity.Warehouse;
import br.com.meli.dhprojetointegrador.entity.Warehouse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgentCollection {
    private Long id;
    private String name;
    private WarehouseDTO warehouse;

    public AgentCollection(Agent agent) {
        this.id = agent.getId();
        this.name = agent.getName();
        this.warehouse = WarehouseDTO
                .builder()
                .id(agent.getWarehouse().getId())
                .name(agent.getWarehouse().getName())
                .build();
    }

    public static List<AgentCollection> convertToList(List<Agent> agents) {
        return agents.stream().map(AgentCollection::new).collect(Collectors.toList());
    }
}
