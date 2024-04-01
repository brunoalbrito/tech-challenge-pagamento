package br.com.fiap.techchallengepagamento.domain;

import java.math.BigDecimal;
import java.util.Set;

public class Pagamento {
    private final Set<Item> items;
    private final BigDecimal valorTotal;
    private final StatusPagamento statusPagamento;

    private Pagamento(Set<Item> items, BigDecimal valorTotal, StatusPagamento statusPagamento) {
        verifica(items, valorTotal);
        this.items = items;
        this.valorTotal = valorTotal;
        this.statusPagamento = statusPagamento;
    }

    public static Pagamento of(Set<Item> items, BigDecimal valorTotal) {
        return new Pagamento(items, valorTotal, StatusPagamento.PENDENTE);
    }

    private void verifica(Set<Item> items, BigDecimal valorTotal) {
        verificaItems(items);
        verificaValorTotal(items, valorTotal);
    }

    private void verificaItems(Set<Item> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Items não podem ser nulos ou vazios");
        }
    }

    private void verificaValorTotal(Set<Item> items, BigDecimal valorTotal) {
        if (valorTotal == null || valorTotal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor total do pagamento deve ser maior que zero");
        }

        BigDecimal valorTotalCalculado = items.stream().map(Item::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (valorTotal.compareTo(valorTotalCalculado) != 0) {
            throw new IllegalArgumentException("Valor total do pagamento deve ser igual ao valor total calculado");
        }
    }

    public Set<Item> getItems() {
        return items;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public Pagamento qrCodeGerado() {
        if (StatusPagamento.PENDENTE != this.statusPagamento) {
            throw new IllegalStateException("Pagamento não está pendente");
        }

        return new Builder()
                .items(this.items)
                .valorTotal(this.valorTotal)
                .statusPagamento(StatusPagamento.AGUARDANDO_APROVACAO)
                .build();
    }

    public Pagamento confirmado() {
        if (StatusPagamento.AGUARDANDO_APROVACAO != this.statusPagamento) {
            throw new IllegalStateException("Pagamento não está aguardando aprovação");
        }

        return new Builder()
                .items(this.items)
                .valorTotal(this.valorTotal)
                .statusPagamento(StatusPagamento.APROVADO)
                .build();
    }

    public Pagamento rejeitado() {
        if (StatusPagamento.AGUARDANDO_APROVACAO != this.statusPagamento) {
            throw new IllegalStateException("Pagamento não está aguardando aprovação");
        }

        return new Builder()
                .items(this.items)
                .valorTotal(this.valorTotal)
                .statusPagamento(StatusPagamento.REJEITADO)
                .build();
    }

    private static class Builder {
        private Set<Item> items;
        private BigDecimal valorTotal;
        private StatusPagamento statusPagamento;

        private Builder items(Set<Item> items) {
            this.items = items;
            return this;
        }

        private Builder valorTotal(BigDecimal valorTotal) {
            this.valorTotal = valorTotal;
            return this;
        }

        private Builder statusPagamento(StatusPagamento statusPagamento) {
            this.statusPagamento = statusPagamento;
            return this;
        }

        public Pagamento build() {
            return new Pagamento(items, valorTotal, statusPagamento);
        }
    }
}
