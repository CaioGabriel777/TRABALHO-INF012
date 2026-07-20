package com.inf012.cliente.mapper;

import org.springframework.stereotype.Component;

import com.inf012.cliente.dto.cliente.ClienteDto;
import com.inf012.cliente.dto.cliente.ClienteResponseDto;
import com.inf012.cliente.model.Cliente;

@Component
public record ClienteMapper(EnderecoMapper enderecoMapper) {

    public Cliente toEntity(ClienteDto cliente) {
        Cliente novoCliente = new Cliente();
        novoCliente.setNome(cliente.nome());
        novoCliente.setCpf(cliente.cpf());
        novoCliente.setEmail(cliente.email());
        novoCliente.setTelefone(cliente.telefone());

        return novoCliente;
    }

    public ClienteResponseDto fromEntity(Cliente cliente) {
        return new ClienteResponseDto(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getEnderecos().stream().map(enderecoMapper::fromEntity).toList(),
                cliente.getDataCriacao(),
                cliente.getDataAtualizacao());
    }
}
