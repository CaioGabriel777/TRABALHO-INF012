package com.inf012.inventory.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.inf012.inventory.model.Fornecedor;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    List<Fornecedor> findByNomeIgnoreCaseContaining(String nome);

    boolean existsByNomeIgnoreCase(String nome);

    boolean existsByCnpj(String cnpj);

    boolean existsByEmail(String email);

    boolean existsByTelefone(String telefone);
}
