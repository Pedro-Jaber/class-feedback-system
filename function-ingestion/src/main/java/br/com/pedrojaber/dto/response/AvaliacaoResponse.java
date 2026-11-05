package br.com.pedrojaber.dto.response;

import java.time.Instant;

import br.com.pedrojaber.domain.Avaliacao;
import br.com.pedrojaber.domain.Urgencia;

public record AvaliacaoResponse(
        String id,
        String descricao,
        int nota,
        Urgencia urgencia,
        Instant dataEnvio) {

    public static AvaliacaoResponse from(Avaliacao avaliacao) {
        return new AvaliacaoResponse(
                avaliacao.getId(),
                avaliacao.getDescricao(),
                avaliacao.getNota(),
                avaliacao.getUrgencia(),
                avaliacao.getDataEnvio());
    }

}
