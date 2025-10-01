package br.com.alura.comex.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResponseDto {
    private Long id;
    private String nome;
    private String cpf;
    private String email; 
    private String telefone;
    private String cidade;
    private String estado;
    private boolean ativo;
}