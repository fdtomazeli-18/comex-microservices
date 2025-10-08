package br.com.alura.produto.repository;

import br.com.alura.produto.model.Categoria;
import org.springframework.data.repository.CrudRepository;

public interface CategoriaRepository extends CrudRepository<Categoria, Long> {
}
