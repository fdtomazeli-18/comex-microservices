package br.com.alura.produto.repository;

import br.com.alura.produto.model.Produto;

import org.springframework.data.repository.CrudRepository;

public interface ProdutoRepository extends CrudRepository<Produto, Long> {
}
