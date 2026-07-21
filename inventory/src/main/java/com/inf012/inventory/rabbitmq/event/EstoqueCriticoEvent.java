package com.inf012.inventory.rabbitmq.event;

public record EstoqueCriticoEvent(
        Long produtoId,
        String nomeProduto,
        Integer quantidadeAtual,
        Integer estoqueMinimo) {
}
