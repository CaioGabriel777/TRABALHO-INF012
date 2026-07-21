package com.inf012.inventory.rabbitmq.event;

public record ProdutoCadastradoEvent(
        Long produtoId,
        String nomeProduto) {
}
