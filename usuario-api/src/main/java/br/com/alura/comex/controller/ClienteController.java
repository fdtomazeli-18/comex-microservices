package br.com.alura.comex.controller;

import br.com.alura.comex.dto.AssociarUsuarioDto;
import br.com.alura.comex.dto.ClienteCreateDto;
import br.com.alura.comex.dto.ClienteResponseDto;
import br.com.alura.comex.response.ApiResponse;
import br.com.alura.comex.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ApiResponse<ClienteResponseDto>> cadastrar(@Valid @RequestBody ClienteCreateDto dto) {
        ClienteResponseDto cliente = clienteService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Cliente criado com sucesso", cliente));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClienteResponseDto>>> listar() {
        List<ClienteResponseDto> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(ApiResponse.success("Clientes listados com sucesso", clientes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteResponseDto>> buscarPorId(@PathVariable Long id) {
        ClienteResponseDto cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(ApiResponse.success("Cliente encontrado", cliente));
    }

    @PostMapping("/{id}/associar-usuario")
    public ResponseEntity<ApiResponse<ClienteResponseDto>> associarUsuario(@PathVariable Long id, @RequestBody @Valid AssociarUsuarioDto dto) {
        ClienteResponseDto cliente = clienteService.associarUsuario(id, dto.getUsuarioId());
        return ResponseEntity.ok(ApiResponse.success("Usuário associado ao cliente com sucesso", cliente));
    }
}