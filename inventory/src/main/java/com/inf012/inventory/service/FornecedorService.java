package com.inf012.inventory.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.inf012.inventory.dto.fornecedor.FornecedorDto;
import com.inf012.inventory.dto.fornecedor.FornecedorResponseDto;
import com.inf012.inventory.dto.fornecedor.FornecedorUpdateDto;
import com.inf012.inventory.exception.BusinessException;
import com.inf012.inventory.exception.ResourceNotFoundException;
import com.inf012.inventory.mapper.FornecedorMapper;
import com.inf012.inventory.model.Fornecedor;
import com.inf012.inventory.repository.FornecedorRepository;

import jakarta.transaction.Transactional;

@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final FornecedorMapper mapper;

    public FornecedorService(FornecedorRepository fornecedorRepository, FornecedorMapper mapper) {
        this.fornecedorRepository = fornecedorRepository;
        this.mapper = mapper;
    }

    @Transactional
    public List<FornecedorResponseDto> listarTodos() {
        List<Fornecedor> fornecedores = fornecedorRepository.findAll();

        if (fornecedores.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum fornecedor encontrado");
        }

        return fornecedores.stream().map(mapper::fromEntity).toList();

    }

    @Transactional
    public FornecedorResponseDto buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Id inválido");
        }

        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado: " + id));

        return mapper.fromEntity(fornecedor);
    }

    @Transactional
    public FornecedorResponseDto cadastrar(FornecedorDto fornecedor) {
        Fornecedor novoFornecedor = mapper.toEntity(fornecedor);

        if (fornecedorRepository.existsByCnpj(fornecedor.cnpj())) {
            throw new BusinessException(HttpStatus.CONFLICT, "CNPJ já cadastrado para outro fornecedor");
        }

        if (fornecedorRepository.existsByEmail(fornecedor.email())) {
            throw new BusinessException(HttpStatus.CONFLICT, "Email já cadastrado para outro fornecedor");
        }

        if (fornecedorRepository.existsByTelefone(fornecedor.telefone())) {
            throw new BusinessException(HttpStatus.CONFLICT, "Telefone já cadastrado para outro fornecedor");
        }

        return mapper.fromEntity(fornecedorRepository.save(novoFornecedor));
    }

    @Transactional
    public FornecedorResponseDto atualizar(Long idFornecedor, FornecedorUpdateDto fornecedor) {

        Fornecedor fornecedorExistente = fornecedorRepository.findById(idFornecedor)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado: " + idFornecedor));

        if (fornecedor.nome() != null) {
            fornecedorExistente.setNome(fornecedor.nome());
        }

        if (fornecedor.cnpj() != null && !fornecedor.cnpj().equals(fornecedorExistente.getCnpj())) {
            if (fornecedorRepository.existsByCnpj(fornecedor.cnpj())) {
                throw new BusinessException(HttpStatus.CONFLICT, "CNPJ já cadastrado para outro fornecedor");
            }
            fornecedorExistente.setCnpj(fornecedor.cnpj());
        }

        if (fornecedor.telefone() != null && !fornecedor.telefone().equals(fornecedorExistente.getTelefone())) {
            if (fornecedorRepository.existsByTelefone(fornecedor.telefone())) {
                throw new BusinessException(HttpStatus.CONFLICT, "Telefone já cadastrado para outro fornecedor");
            }
            fornecedorExistente.setTelefone(fornecedor.telefone());
        }

        if (fornecedor.email() != null && !fornecedor.email().equals(fornecedorExistente.getEmail())) {
            if (fornecedorRepository.existsByEmail(fornecedor.email())) {
                throw new BusinessException(HttpStatus.CONFLICT, "Email já cadastrado para outro fornecedor");
            }
            fornecedorExistente.setEmail(fornecedor.email());
        }

        return mapper.fromEntity(fornecedorRepository.save(fornecedorExistente));
    }

    @Transactional
    public void remover(Long idFornecedor) {
        if (!fornecedorRepository.existsById(idFornecedor)) {
            throw new ResourceNotFoundException("Fornecedor não encontrado: " + idFornecedor);
        }

        fornecedorRepository.deleteById(idFornecedor);
    }
}
