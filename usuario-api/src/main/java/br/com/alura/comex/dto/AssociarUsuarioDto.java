package br.com.alura.comex.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssociarUsuarioDto {
    @NotNull
    private Long usuarioId;
}