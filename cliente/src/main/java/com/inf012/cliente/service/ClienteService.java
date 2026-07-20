package com.inf012.cliente.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.inf012.cliente.dto.cliente.ClienteDto;
import com.inf012.cliente.dto.cliente.ClienteResponseDto;
import com.inf012.cliente.dto.cliente.ClienteUpdateDto;
import com.inf012.cliente.exception.BusinessException;
import com.inf012.cliente.exception.ResourceNotFoundException;
import com.inf012.cliente.mapper.ClienteMapper;
import com.inf012.cliente.model.Cliente;
import com.inf012.cliente.repository.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper mapper;

    public ClienteService(ClienteRepository clienteRepository, ClienteMapper mapper) {
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
    }

    @Transactional
    public List<ClienteResponseDto> listarTodos() {
        List<Cliente> clientes = clienteRepository.findAll();

        if (clientes.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum cliente encontrado");
        }

        return clientes.stream().map(mapper::fromEntity).toList();
    }

    @Transactional
    public ClienteResponseDto buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Id inválido");
        }

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado: " + id));

        return mapper.fromEntity(cliente);
    }

    @Transactional
    public ClienteResponseDto cadastrar(ClienteDto cliente) {
        Cliente novoCliente = mapper.toEntity(cliente);

        if (clienteRepository.existsByCpf(cliente.cpf())) {
            throw new BusinessException(HttpStatus.CONFLICT, "CPF inválido");
        }

        if (clienteRepository.existsByEmail(cliente.email())) {
            throw new BusinessException(HttpStatus.CONFLICT, "Email inválido");
        }

        return mapper.fromEntity(clienteRepository.save(novoCliente));
    }

    @Transactional
    public ClienteResponseDto atualizar(Long idCliente, ClienteUpdateDto cliente) {

        Cliente clienteExistente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado: " + idCliente));

        if (cliente.nome() != null) {
            clienteExistente.setNome(cliente.nome());
        }

        if (cliente.cpf() != null && !cliente.cpf().equals(clienteExistente.getCpf())) {
            if (clienteRepository.existsByCpf(cliente.cpf())) {
                throw new BusinessException(HttpStatus.CONFLICT, "CPF inválido.");
            }
            clienteExistente.setCpf(cliente.cpf());
        }

        if (cliente.email() != null && !cliente.email().equals(clienteExistente.getEmail())) {
            if (clienteRepository.existsByEmail(cliente.email())) {
                throw new BusinessException(HttpStatus.CONFLICT, "Email inválido.");
            }
            clienteExistente.setEmail(cliente.email());
        }

        if (cliente.telefone() != null) {
            clienteExistente.setTelefone(cliente.telefone());
        }

        return mapper.fromEntity(clienteRepository.save(clienteExistente));
    }

    @Transactional
    public void remover(Long idCliente) {
        if (!clienteRepository.existsById(idCliente)) {
            throw new ResourceNotFoundException("Cliente não encontrado: " + idCliente);
        }

        clienteRepository.deleteById(idCliente);
    }
}
