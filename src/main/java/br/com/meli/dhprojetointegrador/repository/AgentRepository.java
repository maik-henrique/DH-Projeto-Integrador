package br.com.meli.dhprojetointegrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.meli.dhprojetointegrador.entity.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long>  {

}
