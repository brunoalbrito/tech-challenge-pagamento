package br.com.fiap.techchallengepagamento.fake;

import br.com.fiap.techchallengepagamento.domain.Item;

import java.math.BigDecimal;
import java.util.UUID;

public class ItemFake {

    public static Item fake() {
        return Item.of(UUID.randomUUID().toString(), "categoria", "titulo", "descricao", BigDecimal.TEN, 1);
    }
}
