package com.inf012.compras.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.inf012.compras.model.ItemCompra;

@Repository
public interface ItemCompraRepository extends JpaRepository<ItemCompra, Long> {
    List<ItemCompra> findByCompraId(Long compraId);
}
