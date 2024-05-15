package br.com.fiap.techchallengepagamento.infrastructure.persistence.gateways.adapters;

import br.com.fiap.techchallengepagamento.application.gateways.PagamentoGateway;
import br.com.fiap.techchallengepagamento.domain.Pagamento;
import br.com.fiap.techchallengepagamento.infrastructure.persistence.entity.PagamentoEntity;
import br.com.fiap.techchallengepagamento.infrastructure.persistence.repository.PagamentoJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PagamentoRepositoryAdapter implements PagamentoGateway {

    private final PagamentoJpaRepository pagamentoJpaRepository;

    public PagamentoRepositoryAdapter(PagamentoJpaRepository pagamentoJpaRepository) {
        this.pagamentoJpaRepository = pagamentoJpaRepository;
    }

    public Pagamento salvaPagamento(Pagamento pagamento) {
        return pagamentoJpaRepository.save(PagamentoEntity.fromDomain(pagamento)).toDomain();
    }
}
