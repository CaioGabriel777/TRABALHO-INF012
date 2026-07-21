package com.inf012.compras.dto.endereco;

import com.inf012.compras.feign.TipoEndereco;

public record EnderecoDto(
        Long id,
        String logradouro,
        String numero,
        String bairro,
        String cidade,
        String estado,
        String cep,
        TipoEndereco tipo) {
}
