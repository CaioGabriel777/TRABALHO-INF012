package com.inf012.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EstoqueDto(
        @NotNull(message = "O id do produto deve ser preenchido") Long produtoId,
        @NotBlank(message = "O tipo da movimentação deve ser preenchido") String tipo,
        @NotNull(message = "A quantidade deve ser preenchida") Integer quantidade,
        String motivo) {

}
