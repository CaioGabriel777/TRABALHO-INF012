package com.inf012.cliente.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.inf012.cliente.model.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    List<Endereco> findByClienteId(Long clienteId);

    List<Endereco> findByClienteIdAndTipo(Long clienteId, String tipo);
}
