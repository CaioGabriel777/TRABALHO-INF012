package com.inf012.inventory.dto.movimentacao;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MovimentacaoDto(
                @NotNull(message = "O id do produto deve ser preenchido") Long produtoId,

                @NotBlank(message = "O tipo da movimentação deve ser preenchido") String tipo,

                @NotNull(message = "A quantidade deve ser preenchida") @Min(value = 1, message = "A quantidade deve ser maior que 0") Integer quantidade,

                @NotBlank(message = "O motivo deve ser preenchido") @Size(min = 1, max = 255) String motivo) {

}
