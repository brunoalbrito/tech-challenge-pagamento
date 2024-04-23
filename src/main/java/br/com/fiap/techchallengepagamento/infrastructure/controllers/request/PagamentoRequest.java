package br.com.fiap.techchallengepagamento.infrastructure.controllers.request;


import br.com.fiap.techchallengepagamento.domain.Item;
import br.com.fiap.techchallengepagamento.domain.Pagamento;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record PagamentoRequest(
        Set<ItemRequest> items,
        BigDecimal valorTotal
) {
    public Pagamento toDomain() {
        List<Item> items = this.items.stream()
                .map(ItemRequest::toDomain)
                .collect(Collectors.toList());

        return Pagamento.of(items, valorTotal);
    }
}
