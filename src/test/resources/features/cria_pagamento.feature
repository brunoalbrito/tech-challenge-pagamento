# language: pt

Funcionalidade: Pagamento

  Cenario: Cria pagamento com sucesso
    Dado uma requisição de pagamento valida
    Quando enviado um POST para "/v1/pagamento"
    Entao a resposta deve ser 201
    E o response deve conter um QRCode