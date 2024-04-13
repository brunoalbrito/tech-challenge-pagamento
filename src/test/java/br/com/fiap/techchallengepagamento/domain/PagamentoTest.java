package br.com.fiap.techchallengepagamento.domain;

import br.com.fiap.techchallengepagamento.fake.ItemFake;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PagamentoTest {

    private final String QR_CODE = "QR_CODE_TESTE_123";

    @Test
    public void deveCriarPagamentoValido() {

        List<Item> items = new ArrayList<>();
        items.add(ItemFake.fake());
        items.add(ItemFake.fake());
        items.add(ItemFake.fake());

        BigDecimal valorTotal = BigDecimal.valueOf(30);
        StatusPagamento statusPagamento = StatusPagamento.PENDENTE;

        Pagamento pagamento = Pagamento.of(items, valorTotal);

        assertEquals(items, pagamento.getItems());
        assertEquals(valorTotal, pagamento.getValorTotal());
        assertEquals(statusPagamento, pagamento.getStatusPagamento());
        assertTrue(pagamento.getQrCode().isEmpty());
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
            Pagamento.of(new ArrayList<>(), BigDecimal.TEN);
        });
        assertEquals("Items não podem ser nulos ou vazios", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoValorTotalForNulo() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            List<Item> items = new ArrayList<>();
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
            List<Item> items = new ArrayList<>();
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
            List<Item> items = new ArrayList<>();
            items.add(ItemFake.fake());
            items.add(ItemFake.fake());
            items.add(ItemFake.fake());
            Pagamento.of(items, BigDecimal.valueOf(100));
        });
        assertEquals("Valor total do pagamento deve ser igual ao valor total calculado", exception.getMessage());
    }

    @Test
    public void deveAtualizarStatusAposGerarQrCode() {
        List<Item> items = new ArrayList<>();
        items.add(ItemFake.fake());
        items.add(ItemFake.fake());
        items.add(ItemFake.fake());

        BigDecimal valorTotal = BigDecimal.valueOf(30);
        Pagamento pagamento = Pagamento.of(items, valorTotal);

        Pagamento aguardandoPagamento = pagamento.qrCodeGerado(QR_CODE);

        assertEquals(StatusPagamento.AGUARDANDO_APROVACAO, aguardandoPagamento.getStatusPagamento());
        assertEquals(QR_CODE, aguardandoPagamento.getQrCode());
    }

    @Test
    public void deveAtualizarStatusAposConfirmarPagamento() {
        List<Item> items = new ArrayList<>();
        items.add(ItemFake.fake());
        items.add(ItemFake.fake());
        items.add(ItemFake.fake());

        BigDecimal valorTotal = BigDecimal.valueOf(30);
        Pagamento pagamento = Pagamento.of(items, valorTotal);
        Pagamento pagamentoAguardando = pagamento.qrCodeGerado(QR_CODE);
        Pagamento pagamentoConfirmado = pagamentoAguardando.confirmado();

        assertEquals(StatusPagamento.APROVADO, pagamentoConfirmado.getStatusPagamento());
    }

    @Test
    public void deveAtualizarStatusAposRejeitarPagamento() {
        List<Item> items = new ArrayList<>();
        items.add(ItemFake.fake());
        items.add(ItemFake.fake());
        items.add(ItemFake.fake());

        BigDecimal valorTotal = BigDecimal.valueOf(30);
        Pagamento pagamento = Pagamento.of(items, valorTotal);
        Pagamento aguardandoPagamento = pagamento.qrCodeGerado(QR_CODE);
        Pagamento rejeitado = aguardandoPagamento.rejeitado();

        assertEquals(StatusPagamento.REJEITADO, rejeitado.getStatusPagamento());
    }

    @Test
    public void deveLancarExcecaoQuandoGerarQrCodeComStatusDiferenteDePendente() {
        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            List<Item> items = new ArrayList<>();
            items.add(ItemFake.fake());
            items.add(ItemFake.fake());
            items.add(ItemFake.fake());

            BigDecimal valorTotal = BigDecimal.valueOf(30);
            Pagamento pagamento = Pagamento.of(items, valorTotal);
            Pagamento pagamentoPendente = pagamento.qrCodeGerado(QR_CODE);
            Pagamento pagamentoPago = pagamentoPendente.confirmado();
            pagamentoPago.qrCodeGerado(QR_CODE);
        });
        assertEquals("Pagamento não está pendente", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoStatusNaoForAguardandoAprovacao() {
        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            List<Item> items = new ArrayList<>();
            items.add(ItemFake.fake());
            items.add(ItemFake.fake());
            items.add(ItemFake.fake());

            BigDecimal valorTotal = BigDecimal.valueOf(30);
            Pagamento pagamento = Pagamento.of(items, valorTotal);
            Pagamento aguardandoPagamento = pagamento.qrCodeGerado(QR_CODE);
            Pagamento pagamentoConfirmando = aguardandoPagamento.confirmado();
            pagamentoConfirmando.confirmado();
        });
        assertEquals("Pagamento não está aguardando aprovação", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoStatusNaoForAguardandoAprovacaoAoRejeitar() {
        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            List<Item> items = new ArrayList<>();
            items.add(ItemFake.fake());
            items.add(ItemFake.fake());
            items.add(ItemFake.fake());

            BigDecimal valorTotal = BigDecimal.valueOf(30);
            Pagamento pagamento = Pagamento.of(items, valorTotal);
            Pagamento aguardandoPagamento = pagamento.qrCodeGerado(QR_CODE);
            Pagamento pagamentoConfirmando = aguardandoPagamento.confirmado();
            pagamentoConfirmando.rejeitado();
        });
        assertEquals("Pagamento não está aguardando aprovação", exception.getMessage());
    }

}
