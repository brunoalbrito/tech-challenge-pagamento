package br.com.fiap.techchallengepagamento.infrastructure.controllers.response;

import br.com.fiap.techchallengepagamento.domain.Pagamento;

import java.util.UUID;

public class PagamentoResponse {

    UUID uuid;
    String qrCode;
    String status;

    public PagamentoResponse(UUID uuid, String qrCode, String status) {
        this.uuid = uuid;
        this.qrCode = qrCode;
        this.status = status;
    }

    public static PagamentoResponse fromDomain(Pagamento pagamento) {
        return new PagamentoResponse(
                pagamento.getUuid(), pagamento.getQrCode(), pagamento.getStatusPagamento().name()
        );
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getStatus() {
        return status;
    }
}
