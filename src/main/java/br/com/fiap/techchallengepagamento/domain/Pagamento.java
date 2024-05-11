package br.com.fiap.techchallengepagamento.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Pagamento {

    private final UUID uuid;
    private final List<Item> items;
    private final BigDecimal valorTotal;
    private final StatusPagamento statusPagamento;
    private final String qrCode;

    private Pagamento(List<Item> items,
                      BigDecimal valorTotal,
                      StatusPagamento statusPagamento,
                      String qrCode) {
        verifica(items, valorTotal);
        this.uuid = UUID.randomUUID();
        this.items = items;
        this.valorTotal = valorTotal;
        this.statusPagamento = statusPagamento;
        this.qrCode = qrCode;
    }

    public static Pagamento of(List<Item> items, BigDecimal valorTotal) {
        return new Builder()
                .items(items)
                .valorTotal(valorTotal)
                .statusPagamento(StatusPagamento.PENDENTE)
                .qrCode("")
                .build();
    }

    public static Pagamento of(List<Item> items, BigDecimal valorTotal, String qrCode, StatusPagamento statusPagamento) {
        return new Builder()
                .items(items)
                .valorTotal(valorTotal)
                .statusPagamento(statusPagamento)
                .qrCode(qrCode)
                .build();
    }

    private void verifica(List<Item> items, BigDecimal valorTotal) {
        verificaItems(items);
        verificaValorTotal(items, valorTotal);
    }

    private void verificaItems(List<Item> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Items não podem ser nulos ou vazios");
        }
    }

    private void verificaValorTotal(List<Item> items, BigDecimal valorTotal) {
        if (valorTotal == null || valorTotal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor total do pagamento deve ser maior que zero");
        }

        BigDecimal valorTotalCalculado = items.stream().map(Item::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (valorTotal.compareTo(valorTotalCalculado) != 0) {
            throw new IllegalArgumentException("Valor total do pagamento deve ser igual ao valor total calculado");
        }
    }

    public Pagamento qrCodeGerado(String qrCode) {
        if (StatusPagamento.PENDENTE != this.statusPagamento) {
            throw new IllegalStateException("Pagamento não está pendente");
        }

        return new Builder()
                .items(this.items)
                .valorTotal(this.valorTotal)
                .statusPagamento(StatusPagamento.AGUARDANDO_APROVACAO)
                .qrCode(qrCode)
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

    public UUID getUuid() {
        return this.uuid;
    }

    public List<Item> getItems() {
        return items;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public String getQrCode() {
        return qrCode;
    }

    private static class Builder {
        private List<Item> items;
        private BigDecimal valorTotal;
        private StatusPagamento statusPagamento;

        private String qrCode;

        private Builder items(List<Item> items) {
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

        private Builder qrCode(String qrCode) {
            this.qrCode = qrCode;
            return this;
        }

        public Pagamento build() {
            return new Pagamento(items, valorTotal, statusPagamento, qrCode);
        }
    }
}

