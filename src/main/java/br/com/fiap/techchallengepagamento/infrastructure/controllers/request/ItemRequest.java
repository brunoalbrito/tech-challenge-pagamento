package br.com.fiap.techchallengepagamento.infrastructure.controllers.request;

import br.com.fiap.techchallengepagamento.domain.Item;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("uuid", this.uuid)
                .append("categoria", this.categoria)
                .append("titulo", this.titulo)
                .append("descricao", this.descricao)
                .append("valorPorUnidade", this.valorPorUnidade)
                .append("quantidade", this.quantidade)
                .toString();
    }
}
