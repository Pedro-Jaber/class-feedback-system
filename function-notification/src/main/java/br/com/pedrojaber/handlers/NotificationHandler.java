package br.com.pedrojaber.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pedrojaber.application.usecases.SendUrgentFeedbackAlertUseCase;
import br.com.pedrojaber.messaging.UrgentFeedbackEvent;
import io.quarkus.logging.Log;
import jakarta.inject.Named;

@Named("notification")
public class NotificationHandler implements RequestHandler<SNSEvent, Void> {

    private SendUrgentFeedbackAlertUseCase sendUrgentFeedbackAlertUseCase;

    private ObjectMapper objectMapper;

    public NotificationHandler(SendUrgentFeedbackAlertUseCase sendUrgentFeedbackAlertUseCase,
            ObjectMapper objectMapper) {
        this.sendUrgentFeedbackAlertUseCase = sendUrgentFeedbackAlertUseCase;
        this.objectMapper = objectMapper;
    }

    @Override
    public Void handleRequest(SNSEvent event, Context context) {

        if (event == null || event.getRecords() == null || event.getRecords().isEmpty()) {
            Log.warn("SNS event received without records");
            return null;
        }

        for (SNSEvent.SNSRecord record : event.getRecords()) {

            String message = record.getSNS().getMessage();

            UrgentFeedbackEvent feedbackEvent = parse(message);
            Log.infof("Urgent feedback event received id=%s", feedbackEvent.id());

            sendUrgentFeedbackAlertUseCase.run(feedbackEvent);

        }

        return null;

    }

    private UrgentFeedbackEvent parse(String message) {
        try {
            return objectMapper.readValue(message, UrgentFeedbackEvent.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid SNS message payload", e);
        }
    }
}