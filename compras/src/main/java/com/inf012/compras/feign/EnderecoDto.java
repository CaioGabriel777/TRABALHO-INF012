package com.inf012.compras.feign;

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
