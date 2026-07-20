package com.inf012.inventory.dto.produto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoDto(
        @NotBlank String nome,

        @NotBlank String descricao,

        @NotNull @DecimalMin("0.0") BigDecimal preco,

        @NotNull @Min(0) Integer quantidadeEstoque,

        @NotNull @Min(0) Integer estoqueMinimo,

        @NotNull Long categoriaId,

        @NotNull Long fornecedorId) {
}
