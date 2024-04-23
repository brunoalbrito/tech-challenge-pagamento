package br.com.fiap.techchallengepagamento.infrastructure.integration.adapters;

import br.com.fiap.techchallengepagamento.application.gateways.PaymentBrokerGateway;
import br.com.fiap.techchallengepagamento.domain.Pagamento;
import org.springframework.stereotype.Component;

@Component
public class MercadoPagoIntegration implements PaymentBrokerGateway {
    @Override
    public String registraPagamento(Pagamento pagamento) {
        return "QR Code";
    }
}
