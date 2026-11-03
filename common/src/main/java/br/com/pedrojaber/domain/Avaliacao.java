package br.com.pedrojaber.domain;

import java.time.Instant;
import java.util.UUID;

public class Avaliacao {

    private String id;
    private String descricao;
    private int nota;
    private Urgencia urgencia;
    private Instant dataEnvio;

    private Avaliacao() {
    }

    private Avaliacao(String id, String descricao, int nota, Urgencia urgencia, Instant dataEnvio) {

        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("descricao é obrigatória");
        }

        if (nota < 0 || nota > 10) {
            throw new IllegalArgumentException("nota deve estar entre 0 e 10");
        }

        this.id = id;
        this.descricao = descricao;
        this.nota = nota;
        this.urgencia = urgencia;
        this.dataEnvio = dataEnvio;
    }

    public static Avaliacao create(String descricao, int nota) {
        return new Avaliacao(
                UUID.randomUUID().toString(),
                descricao,
                nota,
                Urgencia.getUrgencia(nota),
                Instant.now());
    }

    public boolean requireNotification() {
        return urgencia == Urgencia.ALTA;
    }

    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getNota() {
        return nota;
    }

    public Urgencia getUrgencia() {
        return urgencia;
    }

    public Instant getDataEnvio() {
        return dataEnvio;
    }

}
