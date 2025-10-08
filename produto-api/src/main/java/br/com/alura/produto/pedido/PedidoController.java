package br.com.alura.produto.pedido;

import br.com.alura.produto.auth.client.AuthClient;
import br.com.alura.produto.auth.client.TokenValidationResponse;
import br.com.alura.produto.controller.RequestPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private AuthClient authClient;

    @PostMapping
    public ResponseEntity<String> criarPedido(@RequestBody RequestPedido pedido, 
                                            @RequestHeader("Authorization") String bearerToken) {
        try {
            TokenValidationResponse validation = authClient.validateToken(bearerToken);
            
            if (!validation.isValid()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
            }
            
            return ResponseEntity.ok("Pedido criado com sucesso: " + pedido.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro na validação do token");
        }
    }
}