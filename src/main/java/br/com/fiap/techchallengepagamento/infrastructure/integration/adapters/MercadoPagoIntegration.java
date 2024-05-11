package br.com.fiap.techchallengepagamento.infrastructure.integration.adapters;

import br.com.fiap.techchallengepagamento.application.gateways.PaymentBrokerGateway;
import br.com.fiap.techchallengepagamento.domain.Pagamento;
import br.com.fiap.techchallengepagamento.infrastructure.integration.data.request.PagamentoClientRequest;
import br.com.fiap.techchallengepagamento.infrastructure.integration.data.response.PagamentoResponseClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;


@Component
public class MercadoPagoIntegration implements PaymentBrokerGateway {

    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate;

    public MercadoPagoIntegration(ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }


    @Override
    public String registraPagamento(Pagamento pagamento) {
        String apiUrl = "https://api.mercadopago.com/instore/orders/qr/seller/collectors/64579943/pos/TECHCHALLENGEFIAP/qrs";

        String accessToken = "Bearer TEST-252309509037954-012815-07dc98cd6fb3240d4aac5706ada98674-64579943";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", accessToken);

        PagamentoClientRequest pagamentoClientRequest = PagamentoClientRequest.fromPagamento(pagamento);

        HttpEntity<String> requestEntity = new HttpEntity<>(writeAsJson(pagamentoClientRequest), headers);

        ResponseEntity<PagamentoResponseClient> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, PagamentoResponseClient.class);

        return Objects.requireNonNull(responseEntity.getBody()).qrData();
    }

    private String writeAsJson(PagamentoClientRequest pagamentoClient) {
        try {
            return objectMapper.writeValueAsString(pagamentoClient);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
