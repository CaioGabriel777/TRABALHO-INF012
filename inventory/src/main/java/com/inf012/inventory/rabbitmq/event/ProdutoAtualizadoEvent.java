package com.inf012.inventory.rabbitmq.event;

public record ProdutoAtualizadoEvent(
        Long produtoId,
        String nomeProduto) {
}
