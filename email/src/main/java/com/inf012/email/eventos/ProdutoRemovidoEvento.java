package com.inf012.email.eventos;

public record ProdutoRemovidoEvento(
        Long produtoId,
        String nomeProduto) {
}