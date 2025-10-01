package br.com.alura.comex.controller;

import br.com.alura.comex.dto.CategoriaCreateDto;
import br.com.alura.comex.dto.CategoriaResponseDto;
import br.com.alura.comex.dto.CategoriaUpdateDto;
import br.com.alura.comex.response.ApiResponse;
import br.com.alura.comex.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoriaResponseDto>> cadastrar(@Valid @RequestBody CategoriaCreateDto dto) {
        CategoriaResponseDto categoria = categoriaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Categoria criada com sucesso", categoria));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoriaResponseDto>>> listar() {
        List<CategoriaResponseDto> categorias = categoriaService.listarTodas();
        return ResponseEntity.ok(ApiResponse.success("Categorias listadas com sucesso", categorias));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoriaResponseDto>> buscarPorId(@PathVariable Long id) {
        CategoriaResponseDto categoria = categoriaService.buscarPorId(id);
        return ResponseEntity.ok(ApiResponse.success("Categoria encontrada", categoria));
    }
    
    @GetMapping("/paginado")
    public ResponseEntity<ApiResponse<Page<CategoriaResponseDto>>> listarPaginado(Pageable pageable) {
        Page<CategoriaResponseDto> categorias = categoriaService.listarComPaginacao(pageable);
        return ResponseEntity.ok(ApiResponse.success("Categorias listadas com paginação", categorias));
    }
    
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ApiResponse<CategoriaResponseDto>> atualizar(@PathVariable Long id, @Valid @RequestBody CategoriaUpdateDto dto) {
        CategoriaResponseDto categoria = categoriaService.atualizar(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Categoria atualizada com sucesso", categoria));
    }
    
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ApiResponse<Void>> deletar(@PathVariable Long id) {
        categoriaService.deletar(id);
        return ResponseEntity.ok(ApiResponse.success("Categoria deletada com sucesso", null));
    }
    
    @PatchMapping("/reativar/{id}")
    public ResponseEntity<ApiResponse<CategoriaResponseDto>> reativar(@PathVariable Long id) {
        CategoriaResponseDto categoria = categoriaService.reativar(id);
        return ResponseEntity.ok(ApiResponse.success("Categoria reativada com sucesso", categoria));
    }
}