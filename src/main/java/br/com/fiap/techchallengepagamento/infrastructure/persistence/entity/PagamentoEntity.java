package br.com.fiap.techchallengepagamento.infrastructure.persistence.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Pagamentos")
public class PagamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @OneToMany
    @JoinColumn(name = "pagamento_id")
    private List<ItemEntity> items;
    private BigDecimal valorTotal;
    @Column(name = "status_pagamento")
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = StatusPagamentoEntity.class)
    @CollectionTable(name = "pagamento_status_pagamento", joinColumns = @JoinColumn(name = "pagamento_id"))
    private List<StatusPagamentoEntity> statusPagamento;
    private String qrCode;
}
