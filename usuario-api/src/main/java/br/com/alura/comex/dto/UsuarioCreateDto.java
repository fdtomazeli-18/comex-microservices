package br.com.alura.comex.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioCreateDto {

    @NotBlank(message = "Email não pode ser nulo ou vazio")
    @Email(message = "Email deve ter um formato válido")
    private String email;

    @NotBlank(message = "Senha não pode ser nula ou vazia")
    private String senha;
}