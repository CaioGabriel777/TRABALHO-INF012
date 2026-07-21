package com.inf012.email.eventos;

public record ProdutoAtualizadoEvento(
        Long produtoId,
        String nomeProduto) {
}
