package br.com.meli.dhprojetointegrador.repository;

import br.com.meli.dhprojetointegrador.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long>  {
}
