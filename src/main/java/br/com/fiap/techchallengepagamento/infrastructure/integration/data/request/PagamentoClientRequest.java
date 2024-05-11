package br.com.fiap.techchallengepagamento.infrastructure.integration.data.request;

import br.com.fiap.techchallengepagamento.domain.Pagamento;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public record PagamentoClientRequest(
        @JsonProperty("external_reference")
        String externalReference,
        String title,
        String description,
        List<ItemClient> items,
        @JsonProperty("notification_url")
        String notificationUrl,

        @JsonProperty("total_amount")
        BigDecimal totalAmount

) {

    public static PagamentoClientRequest fromPagamento(Pagamento pagamento) {
        var items = pagamento.getItems().stream().map(ItemClient::fromItem).toList();
        return new PagamentoClientRequest(pagamento.getUuid().toString(),
                String.format("Tech Challenge - Pedido %s", pagamento.getUuid()),
                "Tech Challenge - Pedido",
                items,
                "https://webhook.site/7b1b3b7d-0b7d-4b3b-8b3b-7d0b7b3b7d0b",
                pagamento.getValorTotal());
    }
}
