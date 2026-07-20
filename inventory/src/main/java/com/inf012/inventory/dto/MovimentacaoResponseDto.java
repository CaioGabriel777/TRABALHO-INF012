package com.inf012.inventory.dto;

import java.time.LocalDateTime;

public record MovimentacaoResponseDto(
        Long id,
        Long produtoId,
        String tipo,
        Integer quantidade,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao,
        String motivo) {
}
