package com.inf012.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inf012.inventory.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByNomeIgnoreCaseContaining(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}
