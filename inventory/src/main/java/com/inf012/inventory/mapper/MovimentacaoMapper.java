package com.inf012.inventory.mapper;

import org.springframework.stereotype.Component;

import com.inf012.inventory.dto.movimentacao.MovimentacaoResponseDto;
import com.inf012.inventory.model.MovimentacaoEstoque;

@Component
public record MovimentacaoMapper() {

    public MovimentacaoResponseDto fromEntity(MovimentacaoEstoque movimentacao) {
        return new MovimentacaoResponseDto(
                movimentacao.getId(),
                movimentacao.getProduto().getId(),
                movimentacao.getProduto().getNome(),
                movimentacao.getProduto().getQuantidadeEstoque(),
                movimentacao.getTipo().name(),
                movimentacao.getQuantidade(),
                movimentacao.getMotivo(),
                movimentacao.getDataCriacao(),
                movimentacao.getDataAtualizacao());
    }
}
