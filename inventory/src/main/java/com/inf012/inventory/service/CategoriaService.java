package com.inf012.inventory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.inf012.inventory.dto.categoria.CategoriaResponseDto;
import com.inf012.inventory.dto.categoria.CategoriaUpdateDto;
import com.inf012.inventory.exception.ResourceNotFoundException;
import com.inf012.inventory.mapper.CategoriaMapper;
import com.inf012.inventory.model.Categoria;
import com.inf012.inventory.repository.CategoriaRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper mapper;

    public CategoriaService(CategoriaRepository categoriaRepository, CategoriaMapper mapper) {
        this.categoriaRepository = categoriaRepository;
        this.mapper = mapper;
    }

    @Transactional
    public List<CategoriaResponseDto> listarTodas() {
        List<Categoria> categorias = categoriaRepository.findAll();

        if (categorias.isEmpty()) {
            throw new ResourceNotFoundException("Nenhuma categoria foi encontrada.");
        }

        return categorias.stream().map(mapper::fromEntity).toList();
    }

    @Transactional
    public CategoriaResponseDto buscarPorId(Long idCategoria) {
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada: " + idCategoria));

        return mapper.fromEntity(categoria);
    }

    @Transactional
    public Categoria cadastrar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public CategoriaResponseDto atualizar(Long idCategoria, CategoriaUpdateDto categoria) {

        Categoria categoriaExistente = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada: " + idCategoria));

        if (categoria.nome() != null) {
            categoriaExistente.setNome(categoria.nome());
        }

        if (categoria.descricao() != null) {
            categoriaExistente.setDescricao(categoria.descricao());
        }

        return mapper.fromEntity(categoriaRepository.save(categoriaExistente));
    }

    @Transactional
    public void remover(Long idCategoria) {
        if (!categoriaRepository.existsById(idCategoria)) {
            throw new ResourceNotFoundException("Categoria não encontrada: " + idCategoria);
        }

        categoriaRepository.deleteById(idCategoria);
    }
}
