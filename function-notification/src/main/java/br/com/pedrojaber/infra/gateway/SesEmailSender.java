package br.com.pedrojaber.infra.gateway;

import java.util.List;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import br.com.pedrojaber.application.gateway.IEmailSender;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;
import software.amazon.awssdk.services.ses.model.SendEmailResponse;

@ApplicationScoped
public class SesEmailSender implements IEmailSender {

    private static final String CHARSET = "UTF-8";

    private SesClient sesClient;
    private String sender;
    private List<String> recipients;

    public SesEmailSender(
            SesClient sesClient,
            @ConfigProperty(name = "app.ses.sender") String sender,
            @ConfigProperty(name = "app.ses.recipients") List<String> recipients) {
        this.sesClient = sesClient;
        this.sender = sender;
        this.recipients = recipients;
    }

    @Override
    public void send(String subject, String body) {
        SendEmailRequest request = SendEmailRequest.builder()
                .source(sender)
                .destination(d -> d.toAddresses(recipients))
                .message(m -> m
                        .subject(c -> c.data(subject).charset(CHARSET))
                        .body(b -> b.text(c -> c.data(body).charset(CHARSET))))
                .build();

        SendEmailResponse response = sesClient.sendEmail(request);

        Log.infof("E-mail enviado via SES messageId=%s destinatarios=%d",
                response.messageId(), recipients.size());
    }

}
