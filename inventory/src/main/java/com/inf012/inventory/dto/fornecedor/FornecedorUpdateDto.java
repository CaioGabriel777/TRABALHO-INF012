package com.inf012.inventory.dto.fornecedor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record FornecedorUpdateDto(
                @Size(min = 3, max = 255, message = "O nome deve ter entre 3 e 255 caracteres") String nome,

                @Size(min = 14, max = 14, message = "CNPJ deve ter 14 caracteres") String cnpj,

                @Size(min = 11, max = 11, message = "Telefone deve ter 11 caracteres") String telefone,

                @Email(message = "Email inválido") String email) {
}
