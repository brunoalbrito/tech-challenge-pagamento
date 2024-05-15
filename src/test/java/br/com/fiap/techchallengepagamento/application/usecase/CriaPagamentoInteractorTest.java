package br.com.fiap.techchallengepagamento.application.usecase;

import br.com.fiap.techchallengepagamento.application.gateways.PagamentoGateway;
import br.com.fiap.techchallengepagamento.application.gateways.PaymentBrokerGateway;
import br.com.fiap.techchallengepagamento.application.usecases.CriaPagamentoInteractor;
import br.com.fiap.techchallengepagamento.domain.Pagamento;
import br.com.fiap.techchallengepagamento.domain.StatusPagamento;
import br.com.fiap.techchallengepagamento.fake.ItemFake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CriaPagamentoInteractorTest {
    private CriaPagamentoInteractor criaPagamentoInteractor;
    private PagamentoGateway pagamentoGateway;
    private PaymentBrokerGateway paymentBrokerGateway;

    @BeforeEach
    void setUp() {
        pagamentoGateway = Mockito.mock(PagamentoGateway.class);
        paymentBrokerGateway = Mockito.mock(PaymentBrokerGateway.class);
        criaPagamentoInteractor = new CriaPagamentoInteractor(pagamentoGateway, paymentBrokerGateway);
    }


    @Test
    void deveCriarPagamentoComSucesso() {
        Pagamento pagamento = Pagamento.of(List.of(ItemFake.fake()), BigDecimal.TEN);

        String QR_CODE = "QR code";

        when(paymentBrokerGateway.registraPagamento(any(Pagamento.class)))
                .thenReturn(QR_CODE);

        when(pagamentoGateway.salvaPagamento(any(Pagamento.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Pagamento pagamentoCriado = criaPagamentoInteractor.execute(pagamento);

        assertNotNull(pagamentoCriado.getUuid());
        assertEquals(1, pagamentoCriado.getItems().size());
        assertEquals(BigDecimal.TEN, pagamentoCriado.getValorTotal());
        assertEquals(QR_CODE, pagamentoCriado.getQrCode());
        assertEquals(StatusPagamento.AGUARDANDO_APROVACAO, pagamentoCriado.getStatusPagamento());
    }
}
