package br.com.fiap.techchallengepagamento.infrastructure.persistence.entity;

import br.com.fiap.techchallengepagamento.domain.Item;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Items")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private UUID uuid;
    private String categoria;
    private String titulo;
    private String descricao;
    private BigDecimal valorPorUnidade;
    private Integer quantidade;

    public static ItemEntity fromDomain(Item item) {
        var entity = new ItemEntity();
        entity.uuid = UUID.fromString(item.uuid());
        entity.categoria = item.categoria();
        entity.titulo = item.titulo();
        entity.descricao = item.descricao();
        entity.valorPorUnidade = item.valorPorUnidade();
        entity.quantidade = item.quantidade();
        return entity;
    }

    public Item toDomain() {
        return new Item(uuid.toString(), categoria, titulo, descricao, valorPorUnidade, quantidade);
    }
}
