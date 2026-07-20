package com.inf012.email.eventos;

public record EstoqueCriticoEvento(
        Long produtoId,
        String nomeProduto,
        Integer quantidadeAtual,
        Integer estoqueMinimo) {
}
