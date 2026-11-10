package br.com.pedrojaber.infra.gateway;

import java.time.LocalDate;
import java.util.Map;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import br.com.pedrojaber.application.gateway.IFeedbackGateway;
import br.com.pedrojaber.domain.Feedback;
import br.com.pedrojaber.time.TimeZones;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

@ApplicationScoped
public class DynamoFeedbackRepository implements IFeedbackGateway {

    @Inject
    DynamoDbClient dynamoDb;

    @ConfigProperty(name = "app.dynamodb.table")
    private String tableName;

    @Override
    public Feedback save(Feedback feedback) {

        var submissionDay = LocalDate.ofInstant(feedback.getSubmissionDate(), TimeZones.BRAZIL).toString();

        Map<String, AttributeValue> item = Map.of(
                "PK", AttributeValue.fromS(submissionDay),
                "SK", AttributeValue.fromS(feedback.getId()),
                "grade", AttributeValue.fromN(String.valueOf(feedback.getGrade())),
                "description", AttributeValue.fromS(feedback.getDescription()),
                "urgency", AttributeValue.fromS(feedback.getUrgency().name()),
                "submissionDate", AttributeValue.fromS(feedback.getSubmissionDate().toString()));

        dynamoDb.putItem(PutItemRequest.builder()
                .tableName(tableName)
                .item(item)
                .build());

        return feedback;
    }

}
