package com.inf012.inventory.rabbitmq.event;

public record ProdutoRemovidoEvent(
        Long produtoId,
        String nomeProduto) {
}
