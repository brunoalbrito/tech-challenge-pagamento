package br.com.fiap.techchallengepagamento.application.usecases;

import br.com.fiap.techchallengepagamento.application.gateways.PagamentoGateway;
import br.com.fiap.techchallengepagamento.application.gateways.PaymentBrokerGateway;
import br.com.fiap.techchallengepagamento.domain.Pagamento;
import org.springframework.stereotype.Component;

@Component
public class CriaPagamentoInteractor {
    PagamentoGateway pagamentoGateway;

    PaymentBrokerGateway paymentBrokerGateway;

    public Pagamento execute(Pagamento pagamento) {

        String qrCode = paymentBrokerGateway.registraPagamento(pagamento);
        pagamento.qrCodeGerado(qrCode);

        return pagamentoGateway.salvaPagamento(pagamento);
    }
}
