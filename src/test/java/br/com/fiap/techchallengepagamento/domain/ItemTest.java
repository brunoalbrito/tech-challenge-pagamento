package br.com.fiap.techchallengepagamento.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTest {

    @Test
    public void deveCriarItemValido() {
        Item item = Item.of("1", "categoria", "titulo", "descricao", BigDecimal.TEN, 99);
        assertEquals("1", item.getUuid());
        assertEquals("categoria", item.getCategoria());
        assertEquals("titulo", item.getTitulo());
        assertEquals("descricao", item.getDescricao());
        assertEquals(99, item.getQuantidade());
        assertEquals(BigDecimal.TEN, item.getValorPorUnidade());
    }

    @Test
    public void deveLancarExcecaoQuandoUuidProdutoForNulo() {
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Item.of(null, "categoria", "titulo", "descricao", BigDecimal.TEN, 99);
        });
        assertEquals("Uuid do produto não pode ser nulo ou vazio", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoCategoriaForNula() {
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Item.of("1", null, "titulo", "descricao", BigDecimal.TEN, 99);
        });
        assertEquals("Categoria não pode ser nula ou vazia", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoTituloForNulo() {
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Item.of("1", "categoria", null, "descricao", BigDecimal.TEN, 99);
        });
        assertEquals("Título não pode ser nulo ou vazio", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoDescricaoForNula() {
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Item.of("1", "categoria", "titulo", null, BigDecimal.TEN, 99);
        });
        assertEquals("Descrição não pode ser nula ou vazia", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoValorPorUnidadeForNulo() {
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Item.of("1", "categoria", "titulo", "descricao", null, 99);
        });
        assertEquals("Valor por unidade não pode ser nulo", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoValorPorUnidadeForMenorOuIgualAZero() {
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Item.of("1", "categoria", "titulo", "descricao", BigDecimal.ZERO, 99);
        });
        assertEquals("Valor por unidade deve ser maior que zero", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoQuantidadeForMenorOuIgualAZero() {
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Item.of("1", "categoria", "titulo", "descricao", BigDecimal.TEN, 0);
        });
        assertEquals("Quantidade deve ser maior que zero", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoQuantidadeForNula() {
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Item.of("1", "categoria", "titulo", "descricao", BigDecimal.TEN, null);
        });
        assertEquals("Quantidade não pode ser nula", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoCategoriaForVazia() {
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Item.of("1", "", "titulo", "descricao", BigDecimal.TEN, 99);
        });
        assertEquals("Categoria não pode ser nula ou vazia", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoTituloForVazio() {
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Item.of("1", "categoria", "", "descricao", BigDecimal.TEN, 99);
        });
        assertEquals("Título não pode ser nulo ou vazio", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoDescricaoForVazia() {
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Item.of("1", "categoria", "titulo", "", BigDecimal.TEN, 99);
        });
        assertEquals("Descrição não pode ser nula ou vazia", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoUuidProdutoForVazio() {
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Item.of("", "categoria", "titulo", "descricao", BigDecimal.TEN, 99);
        });
        assertEquals("Uuid do produto não pode ser nulo ou vazio", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoValorPorUnidadeForNegativo() {
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Item.of("1", "categoria", "titulo", "descricao", BigDecimal.valueOf(-1), 99);
        });
        assertEquals("Valor por unidade deve ser maior que zero", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoQuantidadeForNegativa() {
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Item.of("1", "categoria", "titulo", "descricao", BigDecimal.TEN, -1);
        });
        assertEquals("Quantidade deve ser maior que zero", exception.getMessage());
    }
}
