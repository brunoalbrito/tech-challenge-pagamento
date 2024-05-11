package br.com.fiap.techchallengepagamento.infrastructure.integration.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PagamentoResponseClient(
        @JsonProperty("qr_data")
        String qrData
) {
    public static PagamentoResponseClient of(String qrData) {
        return new PagamentoResponseClient(qrData);
    }
}
