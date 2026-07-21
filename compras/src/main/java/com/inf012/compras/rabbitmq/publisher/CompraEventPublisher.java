package com.inf012.compras.rabbitmq.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.inf012.compras.rabbitmq.event.CompraRealizadaEvent;

@Component
public class CompraEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public CompraEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarCompraRealizada(CompraRealizadaEvent event) {
        rabbitTemplate.convertAndSend("compra.exchange", "compra.realizada", event);
    }

}
