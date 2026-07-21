package com.inf012.compras.dto.compra;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.inf012.compras.dto.itemcompra.ItemCompraResponseDto;
import com.inf012.compras.model.StatusCompra;
import com.inf012.compras.model.TipoEntrega;

public record CompraResponseDto(
                Long id,
                Long clienteId,
                StatusCompra status,
                TipoEntrega tipoEntrega,
                BigDecimal valorTotal,
                List<ItemCompraResponseDto> itens,
                LocalDateTime dataCriacao,
                LocalDateTime dataAtualizacao) {
}
