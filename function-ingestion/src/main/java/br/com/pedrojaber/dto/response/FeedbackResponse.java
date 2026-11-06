package br.com.pedrojaber.dto.response;

import java.time.Instant;

import br.com.pedrojaber.domain.Feedback;
import br.com.pedrojaber.domain.Urgency;

public record FeedbackResponse(
        String id,
        String description,
        int grade,
        Urgency urgency,
        Instant submissionDate) {

    public static FeedbackResponse from(Feedback feedback) {
        return new FeedbackResponse(
                feedback.getId(),
                feedback.getDescription(),
                feedback.getGrade(),
                feedback.getUrgency(),
                feedback.getSubmissionDate());
    }

}
