package br.com.pedrojaber;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;

import jakarta.inject.Named;

@Named("report")
public class ReportHandler implements RequestHandler<Object, Void> {

    @Override
    public Void handleRequest(Object event, Context context) {
        // function logic
        return null;
    }
}