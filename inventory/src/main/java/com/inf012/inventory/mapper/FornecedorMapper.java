package com.inf012.inventory.mapper;

import org.springframework.stereotype.Component;

import com.inf012.inventory.dto.fornecedor.FornecedorDto;
import com.inf012.inventory.dto.fornecedor.FornecedorResponseDto;
import com.inf012.inventory.model.Fornecedor;

@Component
public record FornecedorMapper() {

    public Fornecedor toEntity(FornecedorDto fornecedor) {
        Fornecedor newFornecedor = new Fornecedor();
        newFornecedor.setNome(fornecedor.nome());
        newFornecedor.setCnpj(fornecedor.cnpj());
        newFornecedor.setTelefone(fornecedor.telefone());
        newFornecedor.setEmail(fornecedor.email());

        return newFornecedor;
    }

    public FornecedorResponseDto fromEntity(Fornecedor fornecedor) {
        return new FornecedorResponseDto(
                fornecedor.getId(),
                fornecedor.getNome(),
                fornecedor.getCnpj(),
                fornecedor.getTelefone(),
                fornecedor.getEmail(),
                fornecedor.getDataCriacao(),
                fornecedor.getDataAtualizacao());
    }
}
