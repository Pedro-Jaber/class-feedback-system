package br.com.pedrojaber.infra.gateway;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import br.com.pedrojaber.application.gateway.IFeedbackGateway;
import br.com.pedrojaber.domain.Feedback;
import br.com.pedrojaber.domain.Urgency;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;

@ApplicationScoped
public class DynamoFeedbackRepository implements IFeedbackGateway {

    private static final String PK = "PK";
    private static final String SK = "SK";
    private static final String GRADE = "grade";
    private static final String DESCRIPTION = "description";
    private static final String URGENCY = "urgency";
    private static final String SUBMISSION_DATE = "submissionDate";

    private final DynamoDbClient dynamoDb;
    private final String tableName;

    public DynamoFeedbackRepository(
            DynamoDbClient dynamoDb,
            @ConfigProperty(name = "app.dynamodb.table") String tableName) {
        this.dynamoDb = dynamoDb;
        this.tableName = tableName;
    }

    @Override
    public List<Feedback> findByPeriod(LocalDate start, LocalDate end) {
        return start.datesUntil(end.plusDays(1))
                .flatMap(this::findByDay)
                .toList();
    }

    private Stream<Feedback> findByDay(LocalDate day) {
        QueryRequest request = QueryRequest.builder()
                .tableName(tableName)
                .keyConditionExpression(PK + " = :day")
                .expressionAttributeValues(Map.of(":day", AttributeValue.fromS(day.toString())))
                .build();

        List<Feedback> feedbacks = dynamoDb.queryPaginator(request)
                .items()
                .stream()
                .map(this::toFeedback)
                .toList();

        Log.debugf("DynamoDB query day=%s items=%d", day, feedbacks.size());

        return feedbacks.stream();
    }

    private Feedback toFeedback(Map<String, AttributeValue> item) {
        return Feedback.restore(
                item.get(SK).s(),
                item.get(DESCRIPTION).s(),
                Integer.parseInt(item.get(GRADE).n()),
                Urgency.valueOf(item.get(URGENCY).s()),
                Instant.parse(item.get(SUBMISSION_DATE).s()));
    }
}
