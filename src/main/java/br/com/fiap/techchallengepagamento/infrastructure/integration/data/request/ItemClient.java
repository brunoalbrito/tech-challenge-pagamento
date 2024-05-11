package br.com.fiap.techchallengepagamento.infrastructure.integration.data.request;

import br.com.fiap.techchallengepagamento.domain.Item;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ItemClient(
        @JsonProperty("sku_number")
        String skuNumber,

        String category,

        String title,

        String description,

        @JsonProperty("unit_price")
        BigDecimal unitPrice,

        Integer quantity,

        @JsonProperty("unit_measure")

        String unitMeasure,

        @JsonProperty("total_amount")
        BigDecimal totalAmount
) {


    public static ItemClient fromItem(Item item) {
        return new ItemClient(
                item.uuid(),
                item.categoria(),
                item.titulo(),
                item.descricao(),
                item.valorPorUnidade(),
                item.quantidade(),
                "unit",
                item.getValorTotal()
        );
    }
}