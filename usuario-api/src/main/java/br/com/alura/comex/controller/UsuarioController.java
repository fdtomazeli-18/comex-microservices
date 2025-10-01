package br.com.alura.comex.controller;

import br.com.alura.comex.dto.UsuarioCreateDto;
import br.com.alura.comex.dto.UsuarioResponseDto;
import br.com.alura.comex.response.ApiResponse;
import br.com.alura.comex.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<ApiResponse<UsuarioResponseDto>> cadastrar(@Valid @RequestBody UsuarioCreateDto dto) {
        UsuarioResponseDto usuario = usuarioService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Usuário criado com sucesso", usuario));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UsuarioResponseDto>>> listar() {
        List<UsuarioResponseDto> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(ApiResponse.success("Usuários listados com sucesso", usuarios));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioResponseDto>> buscarPorId(@PathVariable Long id) {
        UsuarioResponseDto usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(ApiResponse.success("Usuário encontrado", usuario));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ApiResponse<Void>> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.ok(ApiResponse.success("Usuário deletado com sucesso", null));
    }
}