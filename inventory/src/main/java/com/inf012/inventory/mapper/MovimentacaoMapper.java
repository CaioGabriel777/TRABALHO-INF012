package com.inf012.inventory.mapper;

import org.springframework.stereotype.Component;

import com.inf012.inventory.dto.MovimentacaoResponseDto;
import com.inf012.inventory.model.MovimentacaoEstoque;

@Component
public record MovimentacaoMapper() {

    public MovimentacaoResponseDto fromEntity(MovimentacaoEstoque movimentacao) {
        return new MovimentacaoResponseDto(
                movimentacao.getId(),
                movimentacao.getProduto().getId(),
                movimentacao.getTipo().name(),
                movimentacao.getQuantidade(),
                movimentacao.getDataCriacao(),
                movimentacao.getDataAtualizacao(),
                movimentacao.getMotivo());
    }
}
