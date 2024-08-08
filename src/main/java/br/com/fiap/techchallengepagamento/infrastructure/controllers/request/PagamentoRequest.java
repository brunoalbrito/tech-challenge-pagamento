package br.com.fiap.techchallengepagamento.infrastructure.controllers.request;


import br.com.fiap.techchallengepagamento.domain.Item;
import br.com.fiap.techchallengepagamento.domain.Pagamento;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record PagamentoRequest(
        Set<ItemRequest> items,
        BigDecimal valorTotal,
        UUID pedidoId
) {
    public Pagamento toDomain() {
        List<Item> items = this.items.stream()
                .map(ItemRequest::toDomain)
                .collect(Collectors.toList());

        return Pagamento.of(items, this.valorTotal);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("items", this.items)
                .append("valorTotal", this.valorTotal)
                .toString();
    }
}
