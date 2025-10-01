package br.com.alura.comex.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaResponseDto {
    
    private Long id;
    private String nome;
    private boolean ativo;
}