package br.com.fiap.techchallengepagamento.infrastructure.controllers.response;

import br.com.fiap.techchallengepagamento.domain.Pagamento;
import br.com.fiap.techchallengepagamento.domain.StatusPagamento;

import java.util.UUID;

public record MessageTemplate(
        UUID pedidoId,
        String qrCode,
        String status
) {

    public static MessageTemplate fromDomain(UUID pedidoId, Pagamento pagamento, StatusPagamento statusPagamento) {
        return new MessageTemplate(
                pedidoId, pagamento.getQrCode(), statusPagamento.name()
        );
    }
}
