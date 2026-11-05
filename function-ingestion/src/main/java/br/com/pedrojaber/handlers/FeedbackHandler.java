package br.com.pedrojaber.handlers;

import org.jboss.resteasy.reactive.RestResponse;

import br.com.pedrojaber.application.usecases.CreateFeedbackUseCase;
import br.com.pedrojaber.domain.Avaliacao;
import br.com.pedrojaber.dto.request.AvaliacaoRequest;
import br.com.pedrojaber.dto.response.AvaliacaoResponse;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/avaliacao")
public class FeedbackHandler {

    @Inject
    private CreateFeedbackUseCase createFeedbackUseCase;

    @POST
    public RestResponse<AvaliacaoResponse> create(@Valid AvaliacaoRequest avaliacaoRequest) {

        Avaliacao savedAvaliacao = createFeedbackUseCase.run(avaliacaoRequest);

        return RestResponse.status(
                RestResponse.Status.CREATED,
                AvaliacaoResponse.from(savedAvaliacao));
    }

}
