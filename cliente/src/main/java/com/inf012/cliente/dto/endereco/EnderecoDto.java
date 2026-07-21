package com.inf012.cliente.dto.endereco;

import com.inf012.cliente.model.TipoEndereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EnderecoDto(
                @NotNull(message = "O id do cliente deve ser preenchido") Long clienteId,

                @NotBlank(message = "Logradouro é obrigatório") String logradouro,

                String numero,

                String complemento,

                @NotBlank(message = "Bairro é obrigatório") String bairro,

                @NotBlank(message = "Cidade é obrigatória") String cidade,

                @NotBlank(message = "Estado é obrigatório") String estado,

                @NotBlank(message = "CEP é obrigatório") @Size(min = 8, max = 8, message = "CEP deve ter 8 caracteres") String cep,

                @NotNull(message = "O tipo de endereço deve ser preenchido") TipoEndereco tipo) {
}
