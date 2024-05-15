package br.com.fiap.techchallengepagamento.infrastructure.persistence;

import br.com.fiap.techchallengepagamento.domain.Pagamento;
import br.com.fiap.techchallengepagamento.fake.ItemFake;
import br.com.fiap.techchallengepagamento.infrastructure.persistence.gateways.adapters.PagamentoRepositoryAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PagamentoRepositoryAdapterTest {

    @Autowired
    private PagamentoRepositoryAdapter pagamentoRepositoryAdapter;

    @Test
    void deveSalvarPagamentoValido() {
        Pagamento pagamento = Pagamento.of(List.of(ItemFake.fake()), BigDecimal.TEN);

        Pagamento pagamentoSalvo = pagamentoRepositoryAdapter.salvaPagamento(pagamento);

        assertNotNull(pagamentoSalvo.getUuid());
    }


}
