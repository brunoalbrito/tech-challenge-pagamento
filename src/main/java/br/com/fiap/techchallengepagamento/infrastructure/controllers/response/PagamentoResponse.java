package br.com.fiap.techchallengepagamento.infrastructure.controllers.response;

import br.com.fiap.techchallengepagamento.domain.Pagamento;

import java.util.UUID;

public record PagamentoResponse(
        UUID uuid,
        String qrCode,
        String status
) {
    public static PagamentoResponse fromDomain(Pagamento pagamento) {
        return new PagamentoResponse(
                pagamento.getUuid(), pagamento.getQrCode(), pagamento.getStatusPagamento().name()
        );
    }
}
