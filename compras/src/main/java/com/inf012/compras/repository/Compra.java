package com.inf012.compras.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Compra extends JpaRepository<Compra, Long> {
}
