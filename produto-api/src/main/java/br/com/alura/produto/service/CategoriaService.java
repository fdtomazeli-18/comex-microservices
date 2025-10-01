package br.com.alura.produto.service;

import br.com.alura.produto.model.Categoria;
import br.com.alura.produto.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public void cadastrar(Categoria categoria) {
        repository.save(categoria);
    }

    public Optional<Categoria> findById(Long idCategoria) {
        return repository.findById(idCategoria);
    }
}
