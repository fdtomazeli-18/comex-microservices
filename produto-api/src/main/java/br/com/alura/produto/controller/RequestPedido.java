package br.com.alura.produto.controller;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class RequestPedido {

    @Positive(message = "Preço deve ser positivo")
    private BigDecimal preco;

}
