package com.inf012.cliente.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.inf012.cliente.dto.endereco.EnderecoDto;
import com.inf012.cliente.dto.endereco.EnderecoResponseDto;
import com.inf012.cliente.dto.endereco.EnderecoUpdateDto;
import com.inf012.cliente.exception.BusinessException;
import com.inf012.cliente.exception.ResourceNotFoundException;
import com.inf012.cliente.mapper.EnderecoMapper;
import com.inf012.cliente.model.Cliente;
import com.inf012.cliente.model.Endereco;
import com.inf012.cliente.repository.ClienteRepository;
import com.inf012.cliente.repository.EnderecoRepository;

import jakarta.transaction.Transactional;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final ClienteRepository clienteRepository;
    private final EnderecoMapper mapper;

    public EnderecoService(EnderecoRepository enderecoRepository, ClienteRepository clienteRepository,
            EnderecoMapper mapper) {
        this.enderecoRepository = enderecoRepository;
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
    }

    @Transactional
    public List<EnderecoResponseDto> listarPorCliente(Long clienteId) {
        List<Endereco> enderecos = enderecoRepository.findByClienteId(clienteId);

        if (enderecos.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum endereço encontrado para o cliente: " + clienteId);
        }

        return enderecos.stream().map(mapper::fromEntity).toList();
    }

    @Transactional
    public EnderecoResponseDto buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Id inválido");
        }

        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado: " + id));

        return mapper.fromEntity(endereco);
    }

    @Transactional
    public EnderecoResponseDto cadastrar(EnderecoDto endereco) {
        Cliente cliente = clienteRepository.findById(endereco.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        Endereco novoEndereco = mapper.toEntity(endereco);
        novoEndereco.setCliente(cliente);

        return mapper.fromEntity(enderecoRepository.save(novoEndereco));
    }

    @Transactional
    public EnderecoResponseDto atualizar(Long idEndereco, EnderecoUpdateDto endereco) {

        Endereco enderecoExistente = enderecoRepository.findById(idEndereco)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado: " + idEndereco));

        if (endereco.logradouro() != null) {
            enderecoExistente.setLogradouro(endereco.logradouro());
        }

        if (endereco.numero() != null) {
            enderecoExistente.setNumero(endereco.numero());
        }

        if (endereco.complemento() != null) {
            enderecoExistente.setComplemento(endereco.complemento());
        }

        if (endereco.bairro() != null) {
            enderecoExistente.setBairro(endereco.bairro());
        }

        if (endereco.cidade() != null) {
            enderecoExistente.setCidade(endereco.cidade());
        }

        if (endereco.estado() != null) {
            enderecoExistente.setEstado(endereco.estado());
        }

        if (endereco.cep() != null) {
            enderecoExistente.setCep(endereco.cep());
        }

        if (endereco.tipo() != null) {
            enderecoExistente.setTipo(endereco.tipo());
        }

        return mapper.fromEntity(enderecoRepository.save(enderecoExistente));
    }

    @Transactional
    public void remover(Long idEndereco) {
        if (!enderecoRepository.existsById(idEndereco)) {
            throw new ResourceNotFoundException("Endereço não encontrado: " + idEndereco);
        }

        enderecoRepository.deleteById(idEndereco);
    }
}
