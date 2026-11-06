package br.com.pedrojaber.application.messaging;

import br.com.pedrojaber.domain.Feedback;

public interface IFeedbackNotificationPublisher {

    public void publishHighUrgencyFeedback(Feedback savedFeedback);

}
