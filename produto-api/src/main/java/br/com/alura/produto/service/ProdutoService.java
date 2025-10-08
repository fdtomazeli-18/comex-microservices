package br.com.alura.produto.service;

import br.com.alura.produto.model.Produto;
import br.com.alura.produto.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public void cadastrar(Produto obj) {
        repository.save(obj);
    }

    public Iterable<Produto> listar() {
        return repository.findAll();
    }
}
