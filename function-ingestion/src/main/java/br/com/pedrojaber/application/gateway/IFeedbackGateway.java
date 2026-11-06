package br.com.pedrojaber.application.gateway;

import br.com.pedrojaber.domain.Feedback;

public interface IFeedbackGateway {

    public Feedback save(Feedback feedback);
}
