package com.inf012.inventory.service;

import org.springframework.stereotype.Service;

import com.inf012.inventory.dto.EstoqueDto;
import com.inf012.inventory.dto.MovimentacaoResponseDto;
import com.inf012.inventory.mapper.MovimentacaoMapper;
import com.inf012.inventory.model.MovimentacaoEstoque;
import com.inf012.inventory.model.Produto;
import com.inf012.inventory.model.TipoMovimentacao;
import com.inf012.inventory.repository.MovimentacaoEstoqueRepository;
import com.inf012.inventory.repository.ProdutoRepository;

@Service
public class EstoqueService {

    private final ProdutoRepository produtoRepository;
    private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
    private final MovimentacaoMapper mapper;

    public EstoqueService(ProdutoRepository produtoRepository,
            MovimentacaoEstoqueRepository movimentacaoEstoqueRepository, MovimentacaoMapper mapper) {
        this.produtoRepository = produtoRepository;
        this.movimentacaoEstoqueRepository = movimentacaoEstoqueRepository;
        this.mapper = mapper;
    }

    public MovimentacaoResponseDto registrarMovimentacao(EstoqueDto estoque) {
        Produto produto = produtoRepository.findById(estoque.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (estoque.quantidade() <= 0) {
            throw new RuntimeException("Quantidade deve ser maior que 0");
        }

        TipoMovimentacao tipo = TipoMovimentacao.validarMovimentacao(estoque.tipo());

        int quantidadeFinal = tipo == TipoMovimentacao.ENTRADA ? produto.getQuantidadeEstoque() + estoque.quantidade()
                : produto.getQuantidadeEstoque() - estoque.quantidade();

        // se eu remover mais do que eu tenho em estoque
        if (quantidadeFinal < 0) {
            throw new RuntimeException("Estoque insuficiente");
        }

        MovimentacaoEstoque movimentacao = new MovimentacaoEstoque();
        movimentacao.setProduto(produto);
        movimentacao.setTipo(tipo);
        movimentacao.setQuantidade(estoque.quantidade());
        movimentacao.setMotivo(estoque.motivo());

        return mapper.fromEntity(movimentacaoEstoqueRepository.save(movimentacao));
    }
}
