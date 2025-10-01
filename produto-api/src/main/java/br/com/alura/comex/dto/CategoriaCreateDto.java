package br.com.alura.comex.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaCreateDto {
    
    @NotBlank(message = "Nome não pode ser nulo ou vazio")
    @Size(min = 2, message = "Nome deve ter pelo menos 2 caracteres")
    private String nome;
}