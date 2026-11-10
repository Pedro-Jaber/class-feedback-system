package br.com.pedrojaber.application.usecases;

import br.com.pedrojaber.application.gateway.IEmailSender;
import br.com.pedrojaber.messaging.UrgentFeedbackEvent;
import br.com.pedrojaber.time.TimeZones;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SendUrgentFeedbackAlertUseCase {

    private static final String SUBJECT = "[URGENTE] Nova avaliação crítica recebida";

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
                event.urgency().getLabel(),
                event.description(),
                event.urgency().getLabel(),
                TimeZones.BRAZIL_DATE_TIME.format(event.submissionDate()),
                event.id());
    }
}
