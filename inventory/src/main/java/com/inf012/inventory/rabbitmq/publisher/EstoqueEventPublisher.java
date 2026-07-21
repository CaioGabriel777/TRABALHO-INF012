package com.inf012.inventory.rabbitmq.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.inf012.inventory.rabbitmq.event.EstoqueCriticoEvent;
import com.inf012.inventory.rabbitmq.event.ProdutoAtualizadoEvent;
import com.inf012.inventory.rabbitmq.event.ProdutoCadastradoEvent;
import com.inf012.inventory.rabbitmq.event.ProdutoRemovidoEvent;

@Component
public class EstoqueEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public EstoqueEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarProdutoCadastrado(ProdutoCadastradoEvent event) {
        rabbitTemplate.convertAndSend("estoque.exchange", "produto.cadastrado", event);
    }

    public void publicarProdutoAtualizado(ProdutoAtualizadoEvent event) {
        rabbitTemplate.convertAndSend("estoque.exchange", "produto.atualizado", event);
    }

    public void publicarProdutoRemovido(ProdutoRemovidoEvent event) {
        rabbitTemplate.convertAndSend("estoque.exchange", "produto.removido", event);
    }

    public void publicarEstoqueCritico(EstoqueCriticoEvent event) {
        rabbitTemplate.convertAndSend("estoque.exchange", "estoque.critico", event);
    }
}
