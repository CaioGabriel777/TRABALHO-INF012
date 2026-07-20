package com.inf012.inventory.dto.categoria;

import java.time.LocalDateTime;

public record CategoriaResponseDto(
                Long id,
                String nome,
                String descricao,
                LocalDateTime dataCriacao,
                LocalDateTime dataAtualizacao) {

}
