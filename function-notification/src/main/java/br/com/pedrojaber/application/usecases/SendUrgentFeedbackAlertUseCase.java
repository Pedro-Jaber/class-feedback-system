package br.com.pedrojaber.application.usecases;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import br.com.pedrojaber.application.gateway.IEmailSender;
import br.com.pedrojaber.domain.Urgency;
import br.com.pedrojaber.messaging.UrgentFeedbackEvent;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SendUrgentFeedbackAlertUseCase {

    private static final String SUBJECT = "[URGENTE] Nova avaliação crítica recebida";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
            .ofPattern("dd/MM/yyyy HH:mm")
            .withZone(ZoneId.of("America/Sao_Paulo"));

    private IEmailSender emailSender;

    public SendUrgentFeedbackAlertUseCase(IEmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void run(UrgentFeedbackEvent event) {
        String body = buildBody(event);
        emailSender.send(SUBJECT, body);
    }

    private String buildBody(UrgentFeedbackEvent event) {
        return """
                Uma nova avaliação com urgência %s foi recebida e requer atenção.

                Descrição: %s
                Urgência: %s
                Data de envio: %s

                ID da avaliação: %s
                """.formatted(
                label(event.urgency()),
                event.description(),
                label(event.urgency()),
                DATE_FORMATTER.format(event.submissionDate()),
                event.id());
    }

    private Object label(Urgency urgency) {
        return switch (urgency) {
            case HIGH -> "ALTA";
            case MEDIUM -> "MÉDIA";
            case LOW -> "BAIXA";
            default -> throw new IllegalArgumentException("Unexpected value: " + urgency);
        };
    }
}
