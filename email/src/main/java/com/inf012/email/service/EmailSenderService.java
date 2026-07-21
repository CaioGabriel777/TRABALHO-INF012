package com.inf012.email.service;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.inf012.email.eventos.CompraRealizadaEvento;
import com.inf012.email.eventos.EstoqueCriticoEvento;
import com.inf012.email.eventos.ProdutoAtualizadoEvento;
import com.inf012.email.eventos.ProdutoCadastradoEvento;
import com.inf012.email.eventos.ProdutoRemovidoEvento;
import com.inf012.email.model.EmailEnviado;
import com.inf012.email.model.TipoEvento;
import com.inf012.email.repository.EmailEnviadoRepository;

@Service
public class EmailSenderService {

    private final JavaMailSender mailSender;
    private final EmailEnviadoRepository emailEnviadoRepository;

    public EmailSenderService(JavaMailSender mailSender,
            EmailEnviadoRepository emailEnviadoRepository) {
        this.mailSender = mailSender;
        this.emailEnviadoRepository = emailEnviadoRepository;
    }

    public void enviarEmailCompraRealizada(CompraRealizadaEvento event) {
        String assunto = "Compra realizada com sucesso";
        String corpo = "Sua compra #" + event.compraId() + " no valor de R$ "
                + event.valorTotal() + " foi confirmada.";
        enviar(event.clienteId().toString(), assunto, corpo, TipoEvento.COMPRA_REALIZADA);
    }

    public void enviarEmailProdutoCadastrado(ProdutoCadastradoEvento event) {
        String assunto = "Novo produto cadastrado";
        String corpo = "O produto " + event.nomeProduto() + " foi cadastrado no sistema.";
        enviar("estoque@sistema.com", assunto, corpo, TipoEvento.PRODUTO_CADASTRADO);
    }

    public void enviarEmailProdutoAtualizado(ProdutoAtualizadoEvento event) {
        String assunto = "Produto atualizado";
        String corpo = "O produto " + event.nomeProduto() + " foi atualizado no sistema.";
        enviar("estoque@sistema.com", assunto, corpo, TipoEvento.PRODUTO_ATUALIZADO);
    }

    public void enviarEmailProdutoRemovido(ProdutoRemovidoEvento event) {
        String assunto = "Produto removido";
        String corpo = "O produto " + event.nomeProduto() + " foi removido do sistema.";
        enviar("estoque@sistema.com", assunto, corpo, TipoEvento.PRODUTO_REMOVIDO);
    }

    public void enviarEmailEstoqueCritico(EstoqueCriticoEvento event) {
        String assunto = "Alerta: estoque crítico";
        String corpo = "O produto " + event.nomeProduto() + " está com estoque crítico ("
                + event.quantidadeAtual() + "/" + event.estoqueMinimo() + ").";
        enviar("estoque@sistema.com", assunto, corpo, TipoEvento.ESTOQUE_CRITICO);
    }

    private void enviar(String destinatario, String assunto, String corpo, TipoEvento tipoEvento) {
        boolean sucesso = true;
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(destinatario);
            message.setSubject(assunto);
            message.setText(corpo);
            mailSender.send(message);
        } catch (MailException e) {
            sucesso = false;
        }

        EmailEnviado registro = new EmailEnviado();
        registro.setDestinatario(destinatario);
        registro.setAssunto(assunto);
        registro.setTipoEvento(tipoEvento);
        registro.setEnviadoComSucesso(sucesso);
        emailEnviadoRepository.save(registro);
    }
}
