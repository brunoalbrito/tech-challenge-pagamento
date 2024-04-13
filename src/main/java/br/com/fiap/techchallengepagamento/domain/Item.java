package br.com.fiap.techchallengepagamento.domain;

import java.math.BigDecimal;

public record Item(String uuid, String categoria, String titulo, String descricao, BigDecimal valorPorUnidade,
                   Integer quantidade) {
    public Item {
        valida(uuid, categoria, titulo, descricao, valorPorUnidade, quantidade);
    }


    public static Item of(String uuid, String categoria, String titulo, String descricao, BigDecimal valorPorUnidade, Integer quantidade) {
        return new Item(uuid, categoria, titulo, descricao, valorPorUnidade, quantidade);
    }

    public void valida(String uuid, String categoria, String titulo, String descricao, BigDecimal valorPorUnidade, Integer quantidade) {
        verificaCategoria(categoria);
        verificaTitulo(titulo);
        verificaDescricao(descricao);
        verificaUuidProduto(uuid);
        verificaValor(valorPorUnidade);
        verificaQuantidade(quantidade);
    }

    public void verificaUuidProduto(String uuidProduto) {
        if (uuidProduto == null || uuidProduto.isBlank()) {
            throw new IllegalArgumentException("Uuid do produto não pode ser nulo ou vazio");
        }
    }

    private void verificaCategoria(String categoria) {
        if (categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException("Categoria não pode ser nula ou vazia");
        }
    }

    private void verificaTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título não pode ser nulo ou vazio");
        }
    }

    private void verificaDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição não pode ser nula ou vazia");
        }
    }

    private void verificaQuantidade(Integer quantidade) {
        if (quantidade == null) {
            throw new IllegalArgumentException("Quantidade não pode ser nula");
        }

        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
    }

    private void verificaValor(BigDecimal valorPorUnidade) {
        if (valorPorUnidade == null) {
            throw new IllegalArgumentException("Valor por unidade não pode ser nulo");
        }

        if (valorPorUnidade.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor por unidade deve ser maior que zero");
        }
    }

    public BigDecimal getValorTotal() {
        return valorPorUnidade.multiply(BigDecimal.valueOf(quantidade));
    }
}
