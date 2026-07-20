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
import com.inf012.inventory.rabbitmq.event.ProdutoAtualizadoEvent;
import com.inf012.inventory.rabbitmq.event.ProdutoCadastradoEvent;
import com.inf012.inventory.rabbitmq.event.ProdutoRemovidoEvent;
import com.inf012.inventory.rabbitmq.publisher.EstoqueEventPublisher;
import com.inf012.inventory.repository.CategoriaRepository;
import com.inf012.inventory.repository.FornecedorRepository;
import com.inf012.inventory.repository.ProdutoRepository;

import jakarta.transaction.Transactional;

@Service
public class ProdutoService {

    private final CategoriaRepository categoriaRepository;
    private final FornecedorRepository fornecedorRepository;
    private final ProdutoRepository produtoRepository;
    private final EstoqueEventPublisher estoqueEventPublisher;
    private final ProdutoMapper mapper;

    public ProdutoService(CategoriaRepository categoriaRepository,
            FornecedorRepository fornecedorRepository,
            ProdutoRepository produtoRepository,
            EstoqueEventPublisher estoqueEventPublisher,
            ProdutoMapper mapper) {
        this.categoriaRepository = categoriaRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.produtoRepository = produtoRepository;
        this.estoqueEventPublisher = estoqueEventPublisher;
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

    @Transactional
    public ProdutoResponseDto cadastrarProduto(ProdutoDto produto) {
        Categoria categoria = categoriaRepository.findById(produto.categoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        Fornecedor fornecedor = fornecedorRepository.findById(produto.fornecedorId())
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado"));

        Produto novoProduto = mapper.dtoToProduto(produto);
        novoProduto.setCategoria(categoria);
        novoProduto.setFornecedor(fornecedor);
        novoProduto.setAtivo(true);

        Produto produtoSalvo = produtoRepository.save(novoProduto);

        try {
            estoqueEventPublisher
                    .publicarProdutoCadastrado(
                            new ProdutoCadastradoEvent(produtoSalvo.getId(), produtoSalvo.getNome()));
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao publicar evento");
        }

        return mapper.fromEntity(produtoSalvo);
    }

    @Transactional
    public ProdutoResponseDto atualizarProduto(Long idProduto, ProdutoUpdateDto produto) {

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

        Produto produtoSalvo = produtoRepository.save(produtoExistente);

        try {
            estoqueEventPublisher.publicarProdutoAtualizado(new ProdutoAtualizadoEvent(
                    produtoSalvo.getId(),
                    produtoSalvo.getNome()));
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao publicar evento");
        }

        return mapper.fromEntity(produtoSalvo);
    }

    @Transactional
    public void removerProduto(Long idProduto) {
        if (idProduto == null || idProduto <= 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Id inválido");
        }

        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        produtoRepository.delete(produto);

        try {
            estoqueEventPublisher.publicarProdutoRemovido(
                    new ProdutoRemovidoEvent(produto.getId(), produto.getNome()));
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao publicar evento");
        }
    }
}
