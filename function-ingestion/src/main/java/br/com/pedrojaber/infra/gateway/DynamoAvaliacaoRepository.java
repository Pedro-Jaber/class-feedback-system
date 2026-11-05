package br.com.pedrojaber.infra.gateway;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import br.com.pedrojaber.application.gateway.IAvaliacaoGateway;
import br.com.pedrojaber.domain.Avaliacao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

@ApplicationScoped
public class DynamoAvaliacaoRepository implements IAvaliacaoGateway {

    @Inject
    DynamoDbClient dynamoDb;

    @ConfigProperty(name = "app.dynamodb.tabela")
    private String tabela;

    @Override
    public Avaliacao save(Avaliacao avaliacao) {

        var dataEnvioString = LocalDate.ofInstant(avaliacao.getDataEnvio(), ZoneId.of("America/Sao_Paulo")).toString();

        Map<String, AttributeValue> item = Map.of(
                "PK", AttributeValue.fromS(dataEnvioString),
                "SK", AttributeValue.fromS(avaliacao.getId()),
                "nota", AttributeValue.fromN(String.valueOf(avaliacao.getNota())),
                "descricao", AttributeValue.fromS(avaliacao.getDescricao()),
                "urgencia", AttributeValue.fromS(avaliacao.getUrgencia().name()),
                "dataEnvio", AttributeValue.fromS(avaliacao.getDataEnvio().toString()));

        dynamoDb.putItem(PutItemRequest.builder()
                .tableName(tabela)
                .item(item)
                .build());

        return avaliacao;
    }

}
