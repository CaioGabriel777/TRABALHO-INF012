package com.inf012.inventory.dto.produto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.inf012.inventory.dto.fornecedor.FornecedorResponseDto;

public record ProdutoResponseDto(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Integer quantidadeEstoque,
        String categoriaNome,
        Boolean ativo,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao,
        FornecedorResponseDto fornecedor) {
}
