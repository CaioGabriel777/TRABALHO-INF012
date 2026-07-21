package com.inf012.email.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final String ESTOQUE_EXCHANGE = "estoque.exchange";
    private static final String COMPRA_EXCHANGE = "compra.exchange";

    @Bean
    public TopicExchange estoqueExchange() {
        return new TopicExchange(ESTOQUE_EXCHANGE);
    }

    @Bean
    public TopicExchange compraExchange() {
        return new TopicExchange(COMPRA_EXCHANGE);
    }

    @Bean
    public Queue produtoCadastradoQueue() {
        return new Queue("produto-cadastrado-queue");
    }

    @Bean
    public Queue produtoAtualizadoQueue() {
        return new Queue("produto-atualizado-queue");
    }

    @Bean
    public Queue produtoRemovidoQueue() {
        return new Queue("produto-removido-queue");
    }

    @Bean
    public Queue estoqueCriticoQueue() {
        return new Queue("estoque-critico-queue");
    }

    @Bean
    public Queue compraRealizadaQueue() {
        return new Queue("compra-realizada-queue");
    }

    @Bean
    public Binding produtoCadastradoBinding(Queue produtoCadastradoQueue, TopicExchange estoqueExchange) {
        return BindingBuilder.bind(produtoCadastradoQueue).to(estoqueExchange).with("produto.cadastrado");
    }

    @Bean
    public Binding produtoAtualizadoBinding(Queue produtoAtualizadoQueue, TopicExchange estoqueExchange) {
        return BindingBuilder.bind(produtoAtualizadoQueue).to(estoqueExchange).with("produto.atualizado");
    }

    @Bean
    public Binding produtoRemovidoBinding(Queue produtoRemovidoQueue, TopicExchange estoqueExchange) {
        return BindingBuilder.bind(produtoRemovidoQueue).to(estoqueExchange).with("produto.removido");
    }

    @Bean
    public Binding estoqueCriticoBinding(Queue estoqueCriticoQueue, TopicExchange estoqueExchange) {
        return BindingBuilder.bind(estoqueCriticoQueue).to(estoqueExchange).with("estoque.critico");
    }

    @Bean
    public Binding compraRealizadaBinding(Queue compraRealizadaQueue, TopicExchange compraExchange) {
        return BindingBuilder.bind(compraRealizadaQueue).to(compraExchange).with("compra.realizada");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setAlwaysConvertToInferredType(true);
        return converter;
    }
}
