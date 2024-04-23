package br.com.fiap.techchallengepagamento.application.gateways;

import br.com.fiap.techchallengepagamento.domain.Pagamento;

public interface PagamentoGateway {
    Pagamento salvaPagamento(Pagamento pagamento);
}
