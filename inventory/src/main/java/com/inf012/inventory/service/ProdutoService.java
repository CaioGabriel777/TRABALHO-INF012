package com.inf012.inventory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.inf012.inventory.dto.ProdutoDto;
import com.inf012.inventory.dto.ProdutoResponseDto;
import com.inf012.inventory.mapper.ProdutoMapper;
import com.inf012.inventory.model.Categoria;
import com.inf012.inventory.model.Fornecedor;
import com.inf012.inventory.model.Produto;
import com.inf012.inventory.repository.CategoriaRepository;
import com.inf012.inventory.repository.FornecedorRepository;
import com.inf012.inventory.repository.ProdutoRepository;

import jakarta.transaction.Transactional;

@Service
public class ProdutoService {

    private final CategoriaRepository categoriaRepository;
    private final FornecedorRepository fornecedorRepository;
    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper mapper;

    public ProdutoService(CategoriaRepository categoriaRepository,
            FornecedorRepository fornecedorRepository,
            ProdutoRepository produtoRepository,
            ProdutoMapper mapper) {
        this.categoriaRepository = categoriaRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.produtoRepository = produtoRepository;
        this.mapper = mapper;
    }

    @Transactional
    public List<ProdutoResponseDto> listarProdutos() {
        List<Produto> produtos = produtoRepository.findAll();

        if (produtos.isEmpty()) {
            throw new RuntimeException("Nenhum produto encontrado");
        }

        return produtos.stream()
                .map(mapper::fromEntity)
                .toList();
    }

    @Transactional
    public ProdutoResponseDto buscarPorId(Long produtoId) {
        if (produtoId == null || produtoId <= 0) {
            throw new RuntimeException("Id inválido");
        }

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        return mapper.fromEntity(produto);
    }

    // adicionar notificação
    @Transactional
    public ProdutoResponseDto cadastrarProduto(ProdutoDto produto) {
        Categoria categoria = categoriaRepository.findById(produto.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        Fornecedor fornecedor = fornecedorRepository.findById(produto.fornecedorId())
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));

        Produto newProduto = mapper.dtoToProduto(produto);
        newProduto.setCategoria(categoria);
        newProduto.setFornecedor(fornecedor);
        newProduto.setAtivo(true);

        return mapper.fromEntity(produtoRepository.save(newProduto));
    }

    // adicionar notificação
    @Transactional
    public void removerProduto(Long produtoId) {
        if (produtoId == null || produtoId <= 0) {
            throw new RuntimeException("Id inválido");
        }

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produtoRepository.delete(produto);
    }
}
