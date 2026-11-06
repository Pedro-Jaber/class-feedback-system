package br.com.pedrojaber.infra.messaging;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pedrojaber.application.messaging.IFeedbackNotificationPublisher;
import br.com.pedrojaber.domain.Feedback;
import br.com.pedrojaber.messaging.UrgentFeedbackEvent;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@ApplicationScoped
public class FeedbackNotificationPublisherImp implements IFeedbackNotificationPublisher {

    @Inject
    SnsClient snsClient;

    @Inject
    ObjectMapper objectMapper;

    @ConfigProperty(name = "app.sns.urgent-feedback-topic")
    String topicArn;

    @Override
    public void publishHighUrgencyFeedback(Feedback savedFeedback) {
        UrgentFeedbackEvent event = UrgentFeedbackEvent.from(savedFeedback);

        String message = toJson(event);

        PublishResponse response = snsClient.publish(PublishRequest.builder()
                .topicArn(topicArn)
                .subject("Urgent feedback received")
                .message(message)
                .build());

        Log.infof("Urgent feedback event published. feedbackId=%s, response=%s",
                event.id(),
                response);
    }

    private String toJson(UrgentFeedbackEvent event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Could not serialize UrgentFeedbackEvent id=" + event.id() + ": ", e);
        }
    }

}
