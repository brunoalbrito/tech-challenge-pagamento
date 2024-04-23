package br.com.fiap.techchallengepagamento.application.usecases;

import br.com.fiap.techchallengepagamento.application.gateways.PagamentoGateway;
import br.com.fiap.techchallengepagamento.application.gateways.PaymentBrokerGateway;
import br.com.fiap.techchallengepagamento.domain.Pagamento;
import org.springframework.stereotype.Component;

@Component
public class CriaPagamentoInteractor {
    private final PagamentoGateway pagamentoGateway;

    private final PaymentBrokerGateway paymentBrokerGateway;

    public CriaPagamentoInteractor(final PagamentoGateway pagamentoGateway, final PaymentBrokerGateway paymentBrokerGateway){
        this.pagamentoGateway = pagamentoGateway;
        this.paymentBrokerGateway = paymentBrokerGateway;
    }


    public Pagamento execute(Pagamento pagamento) {

        String qrCode = paymentBrokerGateway.registraPagamento(pagamento);
        pagamento.qrCodeGerado(qrCode);

        return pagamentoGateway.salvaPagamento(pagamento);
    }
}
