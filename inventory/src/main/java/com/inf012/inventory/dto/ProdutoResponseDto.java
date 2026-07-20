package com.inf012.inventory.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProdutoResponseDto(
                Long id,
                String nome,
                String descricao,
                BigDecimal preco,
                Integer quantidadeEstoque,
                String categoriaNome,
                LocalDateTime dataCriacao,
                LocalDateTime dataAtualizacao,
                Boolean ativo) {
}
