package com.inf012.inventory.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.inf012.inventory.dto.movimentacao.MovimentacaoDto;
import com.inf012.inventory.dto.movimentacao.MovimentacaoResponseDto;
import com.inf012.inventory.exception.BusinessException;
import com.inf012.inventory.exception.ResourceNotFoundException;
import com.inf012.inventory.mapper.MovimentacaoMapper;
import com.inf012.inventory.model.MovimentacaoEstoque;
import com.inf012.inventory.model.Produto;
import com.inf012.inventory.model.TipoMovimentacao;
import com.inf012.inventory.rabbitmq.event.EstoqueCriticoEvent;
import com.inf012.inventory.rabbitmq.publisher.EstoqueEventPublisher;
import com.inf012.inventory.repository.MovimentacaoEstoqueRepository;
import com.inf012.inventory.repository.ProdutoRepository;

import jakarta.transaction.Transactional;

@Service
public class MovimentacaoEstoqueService {

    private final ProdutoRepository produtoRepository;
    private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
    private final MovimentacaoMapper mapper;
    private final EstoqueEventPublisher estoqueEventPublisher;

    public MovimentacaoEstoqueService(ProdutoRepository produtoRepository,
            MovimentacaoEstoqueRepository movimentacaoEstoqueRepository, MovimentacaoMapper mapper,
            EstoqueEventPublisher estoqueEventPublisher) {
        this.produtoRepository = produtoRepository;
        this.movimentacaoEstoqueRepository = movimentacaoEstoqueRepository;
        this.mapper = mapper;
        this.estoqueEventPublisher = estoqueEventPublisher;
    }

    @Transactional
    public MovimentacaoResponseDto registrarMovimentacao(MovimentacaoDto estoque) {
        Produto produto = produtoRepository.findById(estoque.produtoId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        TipoMovimentacao tipo = TipoMovimentacao.validarMovimentacao(estoque.tipo());

        int quantidadeFinal = tipo == TipoMovimentacao.ENTRADA ? produto.getQuantidadeEstoque() + estoque.quantidade()
                : produto.getQuantidadeEstoque() - estoque.quantidade();

        if (quantidadeFinal < 0) {
            throw new BusinessException(HttpStatus.CONFLICT, "Estoque insuficiente para essa movimentação");
        }
        produto.setQuantidadeEstoque(quantidadeFinal);
        Produto produtoSalvo = produtoRepository.save(produto);

        if (produtoSalvo.getQuantidadeEstoque() <= produtoSalvo.getEstoqueMinimo()) {
            estoqueEventPublisher.publicarEstoqueCritico(
                    new EstoqueCriticoEvent(
                            produtoSalvo.getId(),
                            produtoSalvo.getNome(),
                            produtoSalvo.getQuantidadeEstoque(),
                            produtoSalvo.getEstoqueMinimo()));
        }

        MovimentacaoEstoque movimentacao = new MovimentacaoEstoque();
        movimentacao.setProduto(produtoSalvo);
        movimentacao.setTipo(tipo);
        movimentacao.setQuantidade(estoque.quantidade());
        movimentacao.setMotivo(estoque.motivo());

        return mapper.fromEntity(movimentacaoEstoqueRepository.save(movimentacao));
    }
}
