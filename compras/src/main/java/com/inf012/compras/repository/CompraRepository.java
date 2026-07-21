package com.inf012.compras.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inf012.compras.model.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
    List<Compra> findByClienteId(Long clienteId);
}
