package br.com.pedrojaber.handlers;

import org.jboss.resteasy.reactive.RestResponse;

import br.com.pedrojaber.application.usecases.CreateFeedbackUseCase;
import br.com.pedrojaber.domain.Feedback;
import br.com.pedrojaber.dto.request.FeedbackRequest;
import br.com.pedrojaber.dto.response.FeedbackResponse;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/avaliacao")
public class FeedbackHandler {

    @Inject
    private CreateFeedbackUseCase createFeedbackUseCase;

    @GET
    public String healthCheck() {
        return "OK";
    }

    @POST
    public RestResponse<FeedbackResponse> create(@Valid FeedbackRequest feedbackRequest) {

        Feedback savedFeedback = createFeedbackUseCase.run(feedbackRequest);

        return RestResponse.status(
                RestResponse.Status.CREATED,
                FeedbackResponse.from(savedFeedback));
    }

}
