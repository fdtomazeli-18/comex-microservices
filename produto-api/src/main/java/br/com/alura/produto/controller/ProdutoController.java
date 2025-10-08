package br.com.alura.produto.controller;

import br.com.alura.produto.model.Produto;
import br.com.alura.produto.service.CategoriaService;
import br.com.alura.produto.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<Object> listar() {
        return ResponseEntity.ok(produtoService.listar());
    }

    @PostMapping
    public ResponseEntity<Object> cadastra(@RequestBody @Valid RequestProdutoDto request, BindingResult result){

        if(result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Produto domain = request.toProduto(categoriaService);
        produtoService.cadastrar(domain);

        return new ResponseEntity<>(domain, HttpStatus.OK);
    }
}
