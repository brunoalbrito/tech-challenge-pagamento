package br.com.fiap.techchallengepagamento.domain;

import br.com.fiap.techchallengepagamento.fake.ItemFake;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PagamentoTest {

    @Test
    public void deveCriarPagamentoValido() {

        Set<Item> items = new HashSet<>();
        items.add(ItemFake.fake());
        items.add(ItemFake.fake());
        items.add(ItemFake.fake());

        BigDecimal valorTotal = BigDecimal.valueOf(30);
        StatusPagamento statusPagamento = StatusPagamento.PENDENTE;

        Pagamento pagamento = Pagamento.of(items, valorTotal);

        assertEquals(items, pagamento.getItems());
        assertEquals(valorTotal, pagamento.getValorTotal());
        assertEquals(statusPagamento, pagamento.getStatusPagamento());
    }

    @Test
    public void deveLancarExcecaoQuandoItemsForNulo() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Pagamento.of(null, BigDecimal.TEN);
        });
        assertEquals("Items não podem ser nulos ou vazios", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoItemsForVazio() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Pagamento.of(new HashSet<>(), BigDecimal.TEN);
        });
        assertEquals("Items não podem ser nulos ou vazios", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoValorTotalForNulo() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Set<Item> items = new HashSet<>();
            items.add(ItemFake.fake());
            items.add(ItemFake.fake());
            items.add(ItemFake.fake());
            Pagamento.of(items, null);
        });
        assertEquals("Valor total do pagamento deve ser maior que zero", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoValorTotalForMenorOuIgualAZero() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Set<Item> items = new HashSet<>();
            items.add(ItemFake.fake());
            items.add(ItemFake.fake());
            items.add(ItemFake.fake());
            Pagamento.of(items, BigDecimal.ZERO);
        });
        assertEquals("Valor total do pagamento deve ser maior que zero", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoValorTotalForDiferenteDoValorTotalCalculado() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Set<Item> items = new HashSet<>();
            items.add(ItemFake.fake());
            items.add(ItemFake.fake());
            items.add(ItemFake.fake());
            Pagamento.of(items, BigDecimal.valueOf(100));
        });
        assertEquals("Valor total do pagamento deve ser igual ao valor total calculado", exception.getMessage());
    }

    @Test
    public void deveAtualizarStatusAposGerarQrCode() {
        Set<Item> items = new HashSet<>();
        items.add(ItemFake.fake());
        items.add(ItemFake.fake());
        items.add(ItemFake.fake());

        BigDecimal valorTotal = BigDecimal.valueOf(30);
        Pagamento pagamento = Pagamento.of(items, valorTotal);

        Pagamento aguardandoPagamento = pagamento.qrCodeGerado();

        assertEquals(StatusPagamento.AGUARDANDO_APROVACAO, aguardandoPagamento.getStatusPagamento());
    }

    @Test
    public void deveAtualizarStatusAposConfirmarPagamento() {
        Set<Item> items = new HashSet<>();
        items.add(ItemFake.fake());
        items.add(ItemFake.fake());
        items.add(ItemFake.fake());

        BigDecimal valorTotal = BigDecimal.valueOf(30);
        Pagamento pagamento = Pagamento.of(items, valorTotal);
        Pagamento pagamentoAguardando = pagamento.qrCodeGerado();
        Pagamento pagamentoConfirmado = pagamentoAguardando.confirmado();

        assertEquals(StatusPagamento.APROVADO, pagamentoConfirmado.getStatusPagamento());
    }

    @Test
    public void deveAtualizarStatusAposRejeitarPagamento() {
        Set<Item> items = new HashSet<>();
        items.add(ItemFake.fake());
        items.add(ItemFake.fake());
        items.add(ItemFake.fake());

        BigDecimal valorTotal = BigDecimal.valueOf(30);
        Pagamento pagamento = Pagamento.of(items, valorTotal);
        Pagamento aguardandoPagamento = pagamento.qrCodeGerado();
        Pagamento rejeitado = aguardandoPagamento.rejeitado();

        assertEquals(StatusPagamento.REJEITADO, rejeitado.getStatusPagamento());
    }

    @Test
    public void deveLancarExcecaoQuandoGerarQrCodeComStatusDiferenteDePendente() {
        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            Set<Item> items = new HashSet<>();
            items.add(ItemFake.fake());
            items.add(ItemFake.fake());
            items.add(ItemFake.fake());

            BigDecimal valorTotal = BigDecimal.valueOf(30);
            Pagamento pagamento = Pagamento.of(items, valorTotal);
            Pagamento pagamentoPendente = pagamento.qrCodeGerado();
            Pagamento pagamentoPago = pagamentoPendente.confirmado();
            pagamentoPago.qrCodeGerado();
        });
        assertEquals("Pagamento não está pendente", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoStatusNaoForAguardandoAprovacao() {
        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            Set<Item> items = new HashSet<>();
            items.add(ItemFake.fake());
            items.add(ItemFake.fake());
            items.add(ItemFake.fake());

            BigDecimal valorTotal = BigDecimal.valueOf(30);
            Pagamento pagamento = Pagamento.of(items, valorTotal);
            Pagamento aguardandoPagamento = pagamento.qrCodeGerado();
            Pagamento pagamentoConfirmando = aguardandoPagamento.confirmado();
            pagamentoConfirmando.confirmado();
        });
        assertEquals("Pagamento não está aguardando aprovação", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoStatusNaoForAguardandoAprovacaoAoRejeitar() {
        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            Set<Item> items = new HashSet<>();
            items.add(ItemFake.fake());
            items.add(ItemFake.fake());
            items.add(ItemFake.fake());

            BigDecimal valorTotal = BigDecimal.valueOf(30);
            Pagamento pagamento = Pagamento.of(items, valorTotal);
            Pagamento aguardandoPagamento = pagamento.qrCodeGerado();
            Pagamento pagamentoConfirmando = aguardandoPagamento.confirmado();
            pagamentoConfirmando.rejeitado();
        });
        assertEquals("Pagamento não está aguardando aprovação", exception.getMessage());
    }

}
