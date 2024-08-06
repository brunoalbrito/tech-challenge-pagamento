package br.com.fiap.techchallengepagamento.infrastructure.controllers.response;

import br.com.fiap.techchallengepagamento.domain.Pagamento;
import br.com.fiap.techchallengepagamento.domain.StatusPagamento;

import java.util.UUID;

public record MessageTemplate(
        UUID uuid,
        String qrCode,
        String status
)  {

    public static MessageTemplate fromDomain(Pagamento pagamento, StatusPagamento statusPagamento) {
        return new MessageTemplate(
                pagamento.getUuid(), pagamento.getQrCode(), statusPagamento.name()
        );
    }
}
