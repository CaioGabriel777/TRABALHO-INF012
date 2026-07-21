package com.inf012.compras.dto.itemcompra;

import java.math.BigDecimal;

public record ItemCompraResponseDto(
                Long id,
                Long produtoId,
                Integer quantidade,
                BigDecimal precoUnitario) {
}
