package br.com.pedrojaber.application.usecases;

import br.com.pedrojaber.application.gateway.IFeedbackGateway;
import br.com.pedrojaber.domain.Feedback;
import br.com.pedrojaber.dto.request.FeedbackRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CreateFeedbackUseCase {

    @Inject
    IFeedbackGateway feedbackGateway;

    public Feedback run(FeedbackRequest feedbackRequest) {

        Feedback feedback = Feedback.create(
                feedbackRequest.description(),
                feedbackRequest.grade());

        Feedback savedFeedback = feedbackGateway.save(feedback);

        // TODO: publish event if urgency is HIGH

        return savedFeedback;
    }

}
