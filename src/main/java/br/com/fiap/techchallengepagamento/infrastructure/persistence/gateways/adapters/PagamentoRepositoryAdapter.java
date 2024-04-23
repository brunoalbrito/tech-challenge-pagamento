package br.com.fiap.techchallengepagamento.infrastructure.persistence.gateways.adapters;

import br.com.fiap.techchallengepagamento.application.gateways.PagamentoGateway;
import br.com.fiap.techchallengepagamento.domain.Pagamento;
import org.springframework.stereotype.Service;

@Service
public class PagamentoRepositoryAdapter implements PagamentoGateway {
    @Override
    public Pagamento salvaPagamento(Pagamento pagamento) {
        return null;
    }
}
