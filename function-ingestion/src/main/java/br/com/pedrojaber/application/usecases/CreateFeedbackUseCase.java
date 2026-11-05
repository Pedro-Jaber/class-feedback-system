package br.com.pedrojaber.application.usecases;

import br.com.pedrojaber.application.gateway.IAvaliacaoGateway;
import br.com.pedrojaber.domain.Avaliacao;
import br.com.pedrojaber.dto.request.AvaliacaoRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CreateFeedbackUseCase {

    @Inject
    IAvaliacaoGateway avaliacaoGateway;

    public Avaliacao run(AvaliacaoRequest avaliacaoRequest) {

        Avaliacao avaliacao = Avaliacao.create(
                avaliacaoRequest.descricao(),
                avaliacaoRequest.nota());

        Avaliacao savedAvaliacao = avaliacaoGateway.save(avaliacao);
        
        // TODO: publicar evento se ALTA

        return savedAvaliacao;
    }

}
