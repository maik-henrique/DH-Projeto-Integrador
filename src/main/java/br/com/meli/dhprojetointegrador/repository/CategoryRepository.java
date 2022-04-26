package br.com.meli.dhprojetointegrador.repository;

import br.com.meli.dhprojetointegrador.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
