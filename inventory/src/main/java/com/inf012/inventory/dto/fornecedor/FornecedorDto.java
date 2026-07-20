package com.inf012.inventory.dto.fornecedor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FornecedorDto(
                @NotBlank(message = "Nome é obrigatório") @Size(min = 3, max = 255, message = "Nome deve ter entre 3 e 255 caracteres") String nome,

                // fazer um value object para validar melhor do que String
                @NotBlank(message = "CNPJ é obrigatório") @Size(min = 14, max = 14, message = "CNPJ deve ter 14 caracteres") String cnpj,

                // fazer um value object para validar melhor do que String
                @NotBlank(message = "Telefone é obrigatório") @Size(min = 11, max = 11, message = "Telefone deve ter 11 caracteres") String telefone,

                @Email String email) {

}
