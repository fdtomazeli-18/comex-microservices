package br.com.alura.produto.controller;

import br.com.alura.produto.model.Categoria;
import br.com.alura.produto.model.Produto;
import br.com.alura.produto.service.CategoriaService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record RequestProdutoDto(@NotEmpty @Length(min = 3) String nome,
                                String descricao,
                                @Positive BigDecimal preco,
                                @NotNull Long idCategoria) {

    public Produto toProduto(CategoriaService categoriaService) {
        Categoria categoria = categoriaService.findById(this.idCategoria).orElseThrow();
        return new Produto(this.nome, this.descricao, this.preco, categoria);
    }
}
