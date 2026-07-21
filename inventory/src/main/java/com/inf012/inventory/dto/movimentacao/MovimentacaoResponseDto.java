package com.inf012.inventory.dto.movimentacao;

import java.time.LocalDateTime;

public record MovimentacaoResponseDto(
        Long id,
        Long produtoId,
        String produtoNome,
        Integer quantidadeEstoqueAtual,
        String tipo,
        Integer quantidade,
        String motivo,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao) {
}
