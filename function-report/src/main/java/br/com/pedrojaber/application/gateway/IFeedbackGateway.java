package br.com.pedrojaber.application.gateway;

import java.time.LocalDate;
import java.util.List;

import br.com.pedrojaber.domain.Feedback;

public interface IFeedbackGateway {

    public List<Feedback> findByPeriod(LocalDate start, LocalDate end);
}
