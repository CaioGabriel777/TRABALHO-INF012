package com.inf012.email.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.inf012.email.eventos.CompraRealizadaEvento;
import com.inf012.email.eventos.EstoqueCriticoEvento;
import com.inf012.email.eventos.ProdutoAtualizadoEvento;
import com.inf012.email.eventos.ProdutoCadastradoEvento;
import com.inf012.email.eventos.ProdutoRemovidoEvento;
import com.inf012.email.service.EmailSenderService;

@Component
public class EmailEventListener {

    private final EmailSenderService emailSenderService;

    public EmailEventListener(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @RabbitListener(queues = "compra-realizada-queue")
    public void receberCompraRealizada(CompraRealizadaEvento event) {
        emailSenderService.enviarEmailCompraRealizada(event);
    }

    @RabbitListener(queues = "produto-cadastrado-queue")
    public void receberProdutoCadastrado(ProdutoCadastradoEvento event) {
        emailSenderService.enviarEmailProdutoCadastrado(event);
    }

    @RabbitListener(queues = "produto-atualizado-queue")
    public void receberProdutoAtualizado(ProdutoAtualizadoEvento event) {
        emailSenderService.enviarEmailProdutoAtualizado(event);
    }

    @RabbitListener(queues = "produto-removido-queue")
    public void receberProdutoRemovido(ProdutoRemovidoEvento event) {
        emailSenderService.enviarEmailProdutoRemovido(event);
    }

    @RabbitListener(queues = "estoque-critico-queue")
    public void receberEstoqueCritico(EstoqueCriticoEvento event) {
        emailSenderService.enviarEmailEstoqueCritico(event);
    }
}
