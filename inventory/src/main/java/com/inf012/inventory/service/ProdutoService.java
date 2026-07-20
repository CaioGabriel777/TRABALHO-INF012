package com.inf012.inventory.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.inf012.inventory.dto.produto.ProdutoDto;
import com.inf012.inventory.dto.produto.ProdutoResponseDto;
import com.inf012.inventory.dto.produto.ProdutoUpdateDto;
import com.inf012.inventory.exception.BusinessException;
import com.inf012.inventory.exception.ResourceNotFoundException;
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
            throw new ResourceNotFoundException("Nenhum produto encontrado");
        }

        return produtos.stream()
                .map(mapper::fromEntity)
                .toList();
    }

    @Transactional
    public ProdutoResponseDto buscarPorId(Long produtoId) {
        if (produtoId == null || produtoId <= 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Id inválido");
        }

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        return mapper.fromEntity(produto);
    }

    // adicionar notificação
    @Transactional
    public ProdutoResponseDto cadastrarProduto(ProdutoDto produto) {
        Categoria categoria = categoriaRepository.findById(produto.categoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        Fornecedor fornecedor = fornecedorRepository.findById(produto.fornecedorId())
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado"));

        Produto newProduto = mapper.dtoToProduto(produto);
        newProduto.setCategoria(categoria);
        newProduto.setFornecedor(fornecedor);
        newProduto.setAtivo(true);

        return mapper.fromEntity(produtoRepository.save(newProduto));
    }

    // adicionar notificação
    @Transactional
    public ProdutoResponseDto atualizarProdutor(Long idProduto, ProdutoUpdateDto produto) {

        Produto produtoExistente = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        if (produto.nome() != null) {
            produtoExistente.setNome(produto.nome());
        }

        if (produto.descricao() != null) {
            produtoExistente.setDescricao(produto.descricao());
        }

        if (produto.preco() != null) {
            produtoExistente.setPreco(produto.preco());
        }

        if (produto.quantidadeEstoque() != null) {
            produtoExistente.setQuantidadeEstoque(produto.quantidadeEstoque());
        }

        if (produto.estoqueMinimo() != null) {
            produtoExistente.setEstoqueMinimo(produto.estoqueMinimo());
        }

        if (produto.categoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(produto.categoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria inválida"));
            produtoExistente.setCategoria(categoria);
        }

        if (produto.fornecedorId() != null) {
            Fornecedor fornecedor = fornecedorRepository.findById(produto.fornecedorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado"));
            produtoExistente.setFornecedor(fornecedor);
        }

        if (produto.ativo() != null) {
            produtoExistente.setAtivo(produto.ativo());
        }

        return mapper.fromEntity(produtoRepository.save(produtoExistente));
    }

    // adicionar notificação
    @Transactional
    public void removerProduto(Long idProduto) {
        if (!produtoRepository.existsById(idProduto)) {
            throw new ResourceNotFoundException("Produto não encontrado: " + idProduto);
        }

        produtoRepository.deleteById(idProduto);
    }
}
