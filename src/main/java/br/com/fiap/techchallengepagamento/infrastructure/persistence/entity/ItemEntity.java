package br.com.fiap.techchallengepagamento.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
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
}
