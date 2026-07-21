package com.inf012.inventory.dto.produto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record ProdutoUpdateDto(
                @Size(min = 3, max = 255, message = "O nome deve ter entre 3 e 255 caracteres") String nome,

                @Size(min = 3, max = 255, message = "A descrição deve ter entre 3 e 255 caracteres") String descricao,

                @DecimalMin(value = "0.0", message = "O preço deve ser maior ou igual a 0") BigDecimal preco,

                @Min(value = 0, message = "A quantidade deve ser maior ou igual a 0") Integer quantidadeEstoque,

                @Min(value = 0, message = "Estoque mínimo inválido") Integer estoqueMinimo,

                Long categoriaId,

                Long fornecedorId,

                Boolean ativo) {
}
