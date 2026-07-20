package com.inf012.cliente.dto.cliente;

import java.time.LocalDateTime;
import java.util.List;

import com.inf012.cliente.dto.endereco.EnderecoResponseDto;

public record ClienteResponseDto(
                Long id,
                String nome,
                String cpf,
                String email,
                String telefone,
                List<EnderecoResponseDto> enderecos,
                LocalDateTime dataCriacao,
                LocalDateTime dataAtualizacao) {
}
