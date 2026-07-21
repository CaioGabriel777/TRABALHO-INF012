package com.inf012.cliente.dto.endereco;

import com.inf012.cliente.model.TipoEndereco;

import jakarta.validation.constraints.Size;

public record EnderecoUpdateDto(
                String logradouro,

                String numero,

                String complemento,

                String bairro,

                String cidade,

                String estado,

                @Size(min = 8, max = 8, message = "CEP deve ter 8 caracteres") String cep,

                TipoEndereco tipo) {
}
