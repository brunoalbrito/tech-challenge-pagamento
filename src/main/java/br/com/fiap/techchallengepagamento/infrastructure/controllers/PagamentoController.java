package br.com.fiap.techchallengepagamento.infrastructure.controllers;

import br.com.fiap.techchallengepagamento.application.usecases.CriaPagamentoInteractor;
import br.com.fiap.techchallengepagamento.domain.Pagamento;
import br.com.fiap.techchallengepagamento.infrastructure.controllers.request.PagamentoRequest;
import br.com.fiap.techchallengepagamento.infrastructure.controllers.response.PagamentoResponse;
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

    public PagamentoController(CriaPagamentoInteractor criaPagamentoInteractor, RabbitTemplate rabbitTemplate) {
        this.criaPagamentoInteractor = criaPagamentoInteractor;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    public ResponseEntity<PagamentoResponse> criaPagamento(@RequestBody final PagamentoRequest pagamentoRequest) {
        Pagamento pagamento = pagamentoRequest.toDomain();

        Pagamento pagamentoCriado = criaPagamentoInteractor.execute(pagamento);

        PagamentoResponse pagamentoResponse = PagamentoResponse.fromDomain(pagamentoCriado);

        rabbitTemplate.convertAndSend(pagamentoResponse);


        return new ResponseEntity<>(pagamentoResponse, HttpStatus.CREATED);
    }
}
