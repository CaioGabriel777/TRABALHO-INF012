package com.inf012.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inf012.inventory.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    boolean existsByNomeIgnoreCase(String nome);
}
