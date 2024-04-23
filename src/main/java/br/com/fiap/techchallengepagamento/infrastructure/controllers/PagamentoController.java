package br.com.fiap.techchallengepagamento.infrastructure.controllers;

import br.com.fiap.techchallengepagamento.application.usecases.CriaPagamentoInteractor;
import br.com.fiap.techchallengepagamento.domain.Pagamento;
import br.com.fiap.techchallengepagamento.infrastructure.controllers.request.PagamentoRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    private final CriaPagamentoInteractor criaPagamentoInteractor;

    public PagamentoController(CriaPagamentoInteractor criaPagamentoInteractor) {
        this.criaPagamentoInteractor = criaPagamentoInteractor;
    }

    @PostMapping
    public ResponseEntity<?> criaPagamento(@RequestBody final PagamentoRequest pagamentoRequest) {
        Pagamento pagamento = pagamentoRequest.toDomain();
//        criaPagamentoInteractor.execute(pagamento);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
