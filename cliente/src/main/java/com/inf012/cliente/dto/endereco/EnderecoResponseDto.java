package com.inf012.cliente.dto.endereco;

import java.time.LocalDateTime;

import com.inf012.cliente.model.TipoEndereco;

public record EnderecoResponseDto(
                Long id,
                Long clienteId,
                String logradouro,
                String numero,
                String complemento,
                String bairro,
                String cidade,
                String estado,
                String cep,
                TipoEndereco tipo,
                LocalDateTime dataCriacao,
                LocalDateTime dataAtualizacao) {
}
