package br.com.alura.comex.service;

import br.com.alura.comex.model.Usuario;
import br.com.alura.comex.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder encoder;

    public void save(String login, String senha) {
        if (usuarioRepository.findByLogin(login) == null) {
            usuarioRepository.save(new Usuario(login, encoder.encode(senha)));
        }
    }

}
