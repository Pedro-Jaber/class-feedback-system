package br.com.pedrojaber.application.gateway;

public interface IEmailSender {

    void send(String subject, String body);

}
