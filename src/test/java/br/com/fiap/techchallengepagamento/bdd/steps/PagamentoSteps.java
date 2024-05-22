package br.com.fiap.techchallengepagamento.bdd.steps;

import br.com.fiap.techchallengepagamento.application.usecases.CriaPagamentoInteractor;
import br.com.fiap.techchallengepagamento.infrastructure.controllers.PagamentoController;
import br.com.fiap.techchallengepagamento.infrastructure.controllers.request.ItemRequest;
import br.com.fiap.techchallengepagamento.infrastructure.controllers.request.PagamentoRequest;
import br.com.fiap.techchallengepagamento.infrastructure.controllers.response.PagamentoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.it.Quando;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@SpringBootTest
public class PagamentoSteps {

    @Autowired
    private PagamentoController pagamentoController;

    @Autowired
    private CriaPagamentoInteractor criaPagamentoInteractor;

    private MockMvc mockMvc;
    private PagamentoRequest pagamentoRequest;
    private MvcResult mvcResult;

    @Dado("uma requisição de pagamento valida")
    public void uma_requisição_de_pagamento_valida() {
        ItemRequest itemRequest = new ItemRequest(
                UUID.randomUUID().toString(),
                "categora",
                "titulo",
                "descricao",
                BigDecimal.TEN,
                1);


        pagamentoRequest = new PagamentoRequest(
                Set.of(itemRequest),
                BigDecimal.TEN
        );
    }

    @Quando("enviado um POST para {string}")
    public void enviado_um_post_para(String url) throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(pagamentoController).build();

        mvcResult = mockMvc.perform(post(url)
                        .contentType("application/json")
                        .content(asJsonString(pagamentoRequest)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Entao("a resposta deve ser {int}")
    public void a_resposta_deve_ser(Integer status) {
        assertEquals(mvcResult.getResponse().getStatus(), status);
    }

    @Entao("o response deve conter um QRCode")
    public void o_response_deve_conter_um_qr_code() throws UnsupportedEncodingException {
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        PagamentoResponse pagamentoResponse = asObject(jsonResponse, PagamentoResponse.class);
        assertNotNull(pagamentoResponse);
        assertNotNull(pagamentoResponse.qrCode());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T asObject(final String jsonString, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(jsonString, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
