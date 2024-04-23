package br.com.fiap.techchallengepagamento.infrastructure.controllers.request;

import br.com.fiap.techchallengepagamento.domain.Item;

import java.math.BigDecimal;

public record ItemRequest(String uuid,
                          String categoria,
                          String titulo,
                          String descricao,
                          BigDecimal valorPorUnidade,
                          Integer quantidade) {
    public Item toDomain() {
        return Item.of(uuid, categoria, titulo, descricao, valorPorUnidade, quantidade);
    }
}
