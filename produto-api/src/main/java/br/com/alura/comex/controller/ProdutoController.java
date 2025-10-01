package br.com.alura.comex.controller;

import br.com.alura.comex.dto.ProdutoCreateDto;
import br.com.alura.comex.dto.ProdutoResponseDto;
import br.com.alura.comex.dto.ProdutoUpdateDto;
import br.com.alura.comex.response.ApiResponse;
import br.com.alura.comex.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProdutoResponseDto>> cadastrar(@Valid @RequestBody ProdutoCreateDto dto) {
        ProdutoResponseDto produto = produtoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Produto criado com sucesso", produto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProdutoResponseDto>>> listar() {
        List<ProdutoResponseDto> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(ApiResponse.success("Produtos listados com sucesso", produtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProdutoResponseDto>> buscarPorId(@PathVariable Long id) {
        ProdutoResponseDto produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(ApiResponse.success("Produto encontrado", produto));
    }
    
    @GetMapping("/paginado")
    public ResponseEntity<ApiResponse<Page<ProdutoResponseDto>>> listarPaginado(Pageable pageable) {
        Page<ProdutoResponseDto> produtos = produtoService.listarComPaginacao(pageable);
        return ResponseEntity.ok(ApiResponse.success("Produtos listados com paginação", produtos));
    }
    
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ApiResponse<ProdutoResponseDto>> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoUpdateDto dto) {
        ProdutoResponseDto produto = produtoService.atualizar(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Produto atualizado com sucesso", produto));
    }
    
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ApiResponse<Void>> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.ok(ApiResponse.success("Produto deletado com sucesso", null));
    }
    
    @PatchMapping("/reativar/{id}")
    public ResponseEntity<ApiResponse<ProdutoResponseDto>> reativar(@PathVariable Long id) {
        ProdutoResponseDto produto = produtoService.reativar(id);
        return ResponseEntity.ok(ApiResponse.success("Produto reativado com sucesso", produto));
    }
}