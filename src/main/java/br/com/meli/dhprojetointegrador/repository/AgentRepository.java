package br.com.meli.dhprojetointegrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.meli.dhprojetointegrador.entity.Agent;

public interface AgentRepository extends JpaRepository<Agent, Long> {
}
