package com.inf012.email.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "emails_enviados")
public class EmailEnviado extends BaseEntity {

    private String destinatario;

    private String assunto;

    @Enumerated(EnumType.STRING)
    private TipoEvento tipoEvento;

    private Boolean enviadoComSucesso;

    public EmailEnviado() {
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Boolean getEnviadoComSucesso() {
        return enviadoComSucesso;
    }

    public void setEnviadoComSucesso(Boolean enviadoComSucesso) {
        this.enviadoComSucesso = enviadoComSucesso;
    }
}
