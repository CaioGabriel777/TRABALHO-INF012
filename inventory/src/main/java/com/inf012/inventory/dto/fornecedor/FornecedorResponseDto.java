package com.inf012.inventory.dto.fornecedor;

import java.time.LocalDateTime;

public record FornecedorResponseDto(
        Long id,
        String nome,
        String cnpj,
        String telefone,
        String email,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao) {

}
