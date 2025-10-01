package br.com.alura.comex.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteCreateDto {

    @NotBlank(message = "Nome não pode ser nulo ou vazio")
    @Size(min = 2, message = "Nome deve ter pelo menos 2 caracteres")
    private String nome;

    @NotBlank(message = "CPF não pode ser nulo ou vazio")
    private String cpf;

    private String telefone;
    private String logradouro;
    private String bairro;
    private String cidade;

    @Size(min = 2, max = 2, message = "UF deve ter exatamente 2 caracteres")
    private String estado;
    
    private String cep;
}