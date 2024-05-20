package br.com.fiap.techchallengepagamento.infrastructure.integration.adapter;

import br.com.fiap.techchallengepagamento.domain.Pagamento;
import br.com.fiap.techchallengepagamento.fake.ItemFake;
import br.com.fiap.techchallengepagamento.infrastructure.integration.adapters.MercadoPagoIntegration;
import br.com.fiap.techchallengepagamento.infrastructure.integration.data.request.PagamentoClientRequest;
import br.com.fiap.techchallengepagamento.infrastructure.integration.data.response.PagamentoResponseClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public
class MercadoPagoIntegrationTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private MercadoPagoIntegration mercadoPagoIntegration;

    private final String URL = "https://api.mercadopago.com/instore/orders/qr/seller/collectors/64579943/pos/TECHCHALLENGEFIAP/qrs";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRegistrarPagamento() throws JsonProcessingException {

        Pagamento pagamento = Pagamento.of(List.of(ItemFake.fake()), BigDecimal.TEN);

        String jsonRequest = "{\"dummy\":\"request\"}";
        String expectedQrData = "qrData";

        when(objectMapper.writeValueAsString(any(PagamentoClientRequest.class))).thenReturn(jsonRequest);

        PagamentoResponseClient pagamentoResponseClient = PagamentoResponseClient.of(expectedQrData);

        ResponseEntity<PagamentoResponseClient> responseEntity = ResponseEntity.ok(pagamentoResponseClient);

        when(restTemplate.postForEntity(anyString(), any(), eq(PagamentoResponseClient.class)))
                .thenReturn(responseEntity);


        String qrData = mercadoPagoIntegration.registraPagamento(pagamento);


        assertEquals(expectedQrData, qrData);
        verify(restTemplate).postForEntity(anyString(), any(), eq(PagamentoResponseClient.class));

    }
}
