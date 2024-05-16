package br.com.fiap.techchallengepagamento.bdd;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = SpringTestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberConfiguration {
}
