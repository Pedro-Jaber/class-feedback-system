package br.com.pedrojaber.application.usecases;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import br.com.pedrojaber.application.gateway.IEmailSender;
import br.com.pedrojaber.application.gateway.IFeedbackGateway;
import br.com.pedrojaber.application.report.WeeklyReport;
import br.com.pedrojaber.domain.Feedback;
import br.com.pedrojaber.domain.Urgency;
import br.com.pedrojaber.time.TimeZones;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GenerateWeeklyReportUseCase {

    private static final int PERIOD_DAYS = 7;

    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final List<Urgency> URGENCY_ORDER = List.of(Urgency.HIGH, Urgency.MEDIUM, Urgency.LOW);

    private final IFeedbackGateway feedbackGateway;
    private final IEmailSender emailSender;

    public GenerateWeeklyReportUseCase(IFeedbackGateway feedbackGateway, IEmailSender emailSender) {
        this.feedbackGateway = feedbackGateway;
        this.emailSender = emailSender;
    }

    public void run(LocalDate referenceDate) {

        LocalDate start = referenceDate.minusDays(PERIOD_DAYS);
        LocalDate end = referenceDate.minusDays(1);

        List<Feedback> feedbacks = feedbackGateway.findByPeriod(start, end);
        WeeklyReport report = WeeklyReport.from(start, end, feedbacks);

        Log.infof("Weekly report generated start=%s end=%s total=%d", start, end, report.total());

        emailSender.send(buildSubject(report), buildBody(report));
    }

    private String buildSubject(WeeklyReport report) {
        return "Relatório semanal de avaliações - %s a %s"
                .formatted(DATE.format(report.start()), DATE.format(report.end()));
    }

    private String buildBody(WeeklyReport report) {
        StringBuilder sb = new StringBuilder();

        sb.append("Relatório semanal de avaliações\n");
        sb.append("Período: ").append(DATE.format(report.start()))
                .append(" a ").append(DATE.format(report.end())).append("\n\n");

        sb.append("RESUMO\n");
        sb.append("  Total de avaliações: ").append(report.total()).append('\n');
        sb.append("  Média das notas: ").append(formatAverage(report)).append("\n\n");

        sb.append("AVALIAÇÕES POR DIA\n");
        report.countByDay().forEach((day, count) -> sb.append("  ")
                .append(DATE.format(day)).append(": ").append(count).append('\n'));
        sb.append('\n');

        sb.append("AVALIAÇÕES POR URGÊNCIA\n");
        for (Urgency urgency : URGENCY_ORDER) {
            sb.append("  ").append(urgency.getLabel()).append(": ")
                    .append(report.countByUrgency().get(urgency)).append('\n');
        }
        sb.append('\n');

        sb.append("AVALIAÇÕES DO PERÍODO\n");
        if (report.isEmpty()) {
            sb.append("  Nenhuma avaliação recebida no período.\n");
        } else {
            for (Feedback feedback : report.feedbacks()) {
                sb.append("  - [").append(TimeZones.BRAZIL_DATE_TIME.format(feedback.getSubmissionDate())).append("] ")
                        .append("Urgência: ").append(feedback.getUrgency().getLabel())
                        .append(" | Nota: ").append(feedback.getGrade()).append('\n')
                        .append("    ").append(feedback.getDescription()).append('\n');
            }
        }

        return sb.toString();
    }

    private String formatAverage(WeeklyReport report) {
        if (report.averageGrade().isEmpty()) {
            return "sem avaliações no período";
        }
        return String.format(Locale.ROOT, "%.2f", report.averageGrade().getAsDouble()).replace('.', ',');
    }

}
