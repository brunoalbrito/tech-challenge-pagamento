package br.com.fiap.techchallengepagamento.fake;

import br.com.fiap.techchallengepagamento.domain.Item;

import java.math.BigDecimal;

public class ItemFake {

    public static Item fake() {
        return Item.of("uuidProduto", "categoria", "titulo", "descricao", BigDecimal.TEN, 99);
    }
}
