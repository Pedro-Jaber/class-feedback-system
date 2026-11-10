package br.com.pedrojaber.handlers;

import java.time.LocalDate;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import br.com.pedrojaber.application.usecases.GenerateWeeklyReportUseCase;
import br.com.pedrojaber.time.TimeZones;
import io.quarkus.logging.Log;
import jakarta.inject.Named;

@Named("report")
public class ReportHandler implements RequestHandler<Map<String, Object>, Void> {

    private static final String REFERENCE_DATE_KEY = "referenceDate";

    private final GenerateWeeklyReportUseCase generateWeeklyReportUseCase;

    public ReportHandler(GenerateWeeklyReportUseCase generateWeeklyReportUseCase) {
        this.generateWeeklyReportUseCase = generateWeeklyReportUseCase;
    }

    // input para trigger manual: { "referenceDate": "2026-11-15" }
    @Override
    public Void handleRequest(Map<String, Object> input, Context context) {

        LocalDate referenceDate = resolveReferenceDate(input);

        Log.infof("Weekly report triggered referenceDate=%s", referenceDate);

        generateWeeklyReportUseCase.run(referenceDate);

        return null;
    }

    private LocalDate resolveReferenceDate(Map<String, Object> input) {
        if (input != null && input.get(REFERENCE_DATE_KEY) != null) {
            return LocalDate.parse(input.get(REFERENCE_DATE_KEY).toString());
        }
        return LocalDate.now(TimeZones.BRAZIL);
    }

}