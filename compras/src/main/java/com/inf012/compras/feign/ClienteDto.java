package com.inf012.compras.feign;

import java.util.List;

public record ClienteDto(
                Long id,
                String nome,
                String cpf,
                String email,
                List<EnderecoDto> enderecos) {
}
