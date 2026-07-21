package com.inf012.inventory.dto.categoria;

import jakarta.validation.constraints.Size;

public record CategoriaUpdateDto(
        @Size(min = 3, max = 50, message = "Nome deve ter entre 3 e 50 caracteres") String nome,

        @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres") String descricao) {
}
