package com.inf012.inventory.model;

import com.inf012.inventory.exception.BusinessException;

public enum TipoMovimentacao {
    ENTRADA, SAIDA;

    public static TipoMovimentacao validarMovimentacao(String movimentacao) {
        if (movimentacao == null || movimentacao.isEmpty()) {
            throw new BusinessException("Movimentação inválida.");
        }
        for (TipoMovimentacao tipo : TipoMovimentacao.values()) {
            if (tipo.name().equalsIgnoreCase(movimentacao)) {
                return tipo;
            }
        }
        throw new BusinessException("Movimentação inválida: " + movimentacao);
    }
}