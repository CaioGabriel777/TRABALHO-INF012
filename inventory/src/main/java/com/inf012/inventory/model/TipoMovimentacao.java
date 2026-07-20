package com.inf012.inventory.model;

public enum TipoMovimentacao {
    ENTRADA, SAIDA;

    public static TipoMovimentacao validarMovimentacao(String movimentacao) {
        if (movimentacao == null || movimentacao.isEmpty()) {
            throw new RuntimeException("Movimentação inválida.");
        }
        for (TipoMovimentacao tipo : TipoMovimentacao.values()) {
            if (tipo.name().equalsIgnoreCase(movimentacao)) {
                return tipo;
            }
        }
        throw new RuntimeException("Movimentação inválida: " + movimentacao);
    }
}