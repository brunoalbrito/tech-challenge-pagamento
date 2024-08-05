package br.com.fiap.techchallengepagamento.infrastructure.controllers;

import br.com.fiap.techchallengepagamento.application.usecases.CriaPagamentoInteractor;
import br.com.fiap.techchallengepagamento.domain.Item;
import br.com.fiap.techchallengepagamento.domain.Pagamento;
import br.com.fiap.techchallengepagamento.infrastructure.controllers.request.ItemRequest;
import br.com.fiap.techchallengepagamento.infrastructure.controllers.request.PagamentoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@ExtendWith(SpringExtension.class)
@WebMvcTest(PagamentoController.class)
public class PagamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CriaPagamentoInteractor criaPagamentoInteractor;

    @Test
    public void deveCriarPagamentoValido() throws Exception {
        ItemRequest itemRequest = new ItemRequest(
                UUID.randomUUID().toString(),
                "categoria",
                "titulo",
                "descricao",
                BigDecimal.TEN,
                1
        );

        PagamentoRequest pagamentoRequest = new PagamentoRequest(
                Set.of(itemRequest),
                BigDecimal.TEN
        );

        when(criaPagamentoInteractor.execute(Mockito.any(Pagamento.class)))
                .thenReturn(Pagamento.of(List.of(Item.of(
                        itemRequest.uuid(),
                        itemRequest.categoria(),
                        itemRequest.titulo(),
                        itemRequest.descricao(),
                        itemRequest.valorPorUnidade(),
                        itemRequest.quantidade()
                )), pagamentoRequest.valorTotal()));

        mockMvc.perform(post("/v1/pagamento")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pagamentoRequest)))
                .andExpect(status().isCreated());
    }
}
