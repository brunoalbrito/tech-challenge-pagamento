package br.com.fiap.techchallengepagamento.infrastructure.persistence.repository;

import br.com.fiap.techchallengepagamento.infrastructure.persistence.entity.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PagamentoJpaRepository extends JpaRepository<PagamentoEntity, UUID> {
}
