package br.com.alura.produto.controller;

import br.com.alura.produto.model.Categoria;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class RequestCategoriaDto {

    @NotBlank(message = "Campo não pode ser vazio")
    @Length(min = 2, message = "Nome com no mínimo 2 caracteres")
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Categoria toCategoria() {
        return new Categoria(this.nome);
    }
}
