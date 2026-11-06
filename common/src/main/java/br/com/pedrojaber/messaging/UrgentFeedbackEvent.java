package br.com.pedrojaber.messaging;

import java.time.Instant;

import br.com.pedrojaber.domain.Feedback;
import br.com.pedrojaber.domain.Urgency;

public record UrgentFeedbackEvent(
        String id,
        String description,
        Urgency urgency,
        Instant submissionDate) {
    public static UrgentFeedbackEvent from(Feedback feedback) {
        return new UrgentFeedbackEvent(
                feedback.getId(),
                feedback.getDescription(),
                feedback.getUrgency(),
                feedback.getSubmissionDate());
    }

}
