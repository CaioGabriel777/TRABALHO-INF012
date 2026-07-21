package com.inf012.compras.dto.itemcompra;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemCompraDto(
                @NotNull(message = "O id do produto deve ser preenchido") Long produtoId,

                @NotNull(message = "A quantidade deve ser preenchida") @Min(value = 1, message = "A quantidade deve ser maior que 0") Integer quantidade,

                @NotNull(message = "O preço unitário deve ser preenchido") @DecimalMin("0.0") BigDecimal precoUnitario) {
}
