package com.inf012.cliente.mapper;

import org.springframework.stereotype.Component;

import com.inf012.cliente.dto.endereco.EnderecoDto;
import com.inf012.cliente.dto.endereco.EnderecoResponseDto;
import com.inf012.cliente.model.Endereco;

@Component
public record EnderecoMapper() {

    public Endereco toEntity(EnderecoDto endereco) {
        Endereco novoEndereco = new Endereco();
        novoEndereco.setLogradouro(endereco.logradouro());
        novoEndereco.setNumero(endereco.numero());
        novoEndereco.setComplemento(endereco.complemento());
        novoEndereco.setBairro(endereco.bairro());
        novoEndereco.setCidade(endereco.cidade());
        novoEndereco.setEstado(endereco.estado());
        novoEndereco.setCep(endereco.cep());
        novoEndereco.setTipo(endereco.tipo());

        return novoEndereco;
    }

    public EnderecoResponseDto fromEntity(Endereco endereco) {
        return new EnderecoResponseDto(
                endereco.getId(),
                endereco.getCliente().getId(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep(),
                endereco.getTipo(),
                endereco.getDataCriacao(),
                endereco.getDataAtualizacao());
    }
}
