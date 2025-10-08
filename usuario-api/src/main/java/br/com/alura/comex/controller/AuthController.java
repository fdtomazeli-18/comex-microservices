package br.com.alura.comex.controller;

import br.com.alura.comex.model.Usuario;
import br.com.alura.comex.repository.UsuarioRepository;
import br.com.alura.comex.producer.users.UserEventProducer;
import br.com.alura.comex.security.DadosTokenJWT;
import br.com.alura.comex.service.TokenService;
import br.com.alura.comex.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UserEventProducer userEventProducer;

    @PostMapping
    public ResponseEntity<Object> efetuarLogin(@RequestBody @Valid RequestAutenticacao dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);

        Usuario usuario = (Usuario) authentication.getPrincipal();
        var tokenJWT = tokenService.gerarToken(usuario);

        userEventProducer.publishAuthEvent(tokenJWT, usuario.getLogin(), true, "AUTH");

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

    @PostMapping("/init")
    public HttpStatus inicializaNovoUsuario() {

        String login = "admin";
        String senha = "admin123";
        usuarioService.save(login, senha);

        return HttpStatus.CREATED;
    }

    @PostMapping("/token/validation")
    public ResponseEntity<TokenValidationResponse> validateToken(@RequestHeader("Authorization") String bearerToken) {
        try {
            String token = bearerToken.replace("Bearer ", "");
            String subject = tokenService.getSubject(token);
            boolean isValid = subject != null && !subject.isEmpty();
            return ResponseEntity.ok(new TokenValidationResponse(isValid));
        } catch (Exception e) {
            return ResponseEntity.ok(new TokenValidationResponse(false));
        }
    }

    public record TokenValidationResponse(Boolean isValid) {}
}
