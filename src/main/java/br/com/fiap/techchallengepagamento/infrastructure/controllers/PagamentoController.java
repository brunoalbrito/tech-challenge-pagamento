package br.com.fiap.techchallengepagamento.infrastructure.controllers;

import br.com.fiap.techchallengepagamento.application.usecases.CriaPagamentoInteractor;
import br.com.fiap.techchallengepagamento.domain.Pagamento;
import br.com.fiap.techchallengepagamento.domain.StatusPagamento;
import br.com.fiap.techchallengepagamento.infrastructure.controllers.request.PagamentoRequest;
import br.com.fiap.techchallengepagamento.infrastructure.controllers.response.MessageTemplate;
import br.com.fiap.techchallengepagamento.infrastructure.controllers.response.PagamentoResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/pagamento")
public class PagamentoController {

    private final CriaPagamentoInteractor criaPagamentoInteractor;

    private final RabbitTemplate rabbitTemplate;


    private final ObjectMapper objectMapper;

    public PagamentoController(CriaPagamentoInteractor criaPagamentoInteractor, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.criaPagamentoInteractor = criaPagamentoInteractor;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public ResponseEntity<PagamentoResponse> criaPagamento(@RequestBody final PagamentoRequest pagamentoRequest) throws JsonProcessingException {
        Pagamento pagamento = pagamentoRequest.toDomain();

        Pagamento pagamentoCriado = criaPagamentoInteractor.execute(pagamento);

        PagamentoResponse pagamentoResponse = PagamentoResponse.fromDomain(pagamentoCriado);

        MessageTemplate messageTemplate = MessageTemplate.fromDomain(pagamentoRequest.pedidoId(), pagamentoCriado, StatusPagamento.APROVADO);

        rabbitTemplate.convertAndSend("pagamentoQueue", objectMapper.writeValueAsString(messageTemplate));

        return new ResponseEntity<>(pagamentoResponse, HttpStatus.CREATED);
    }
}
