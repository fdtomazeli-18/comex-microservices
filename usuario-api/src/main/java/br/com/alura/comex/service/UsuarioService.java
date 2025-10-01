package br.com.alura.comex.service;

import br.com.alura.comex.dto.UsuarioCreateDto;
import br.com.alura.comex.dto.UsuarioResponseDto;
import br.com.alura.comex.exception.EntidadeNaoEncontradaException;
import br.com.alura.comex.exception.UsuarioJaExisteException;
import br.com.alura.comex.mapper.UsuarioMapper;
import br.com.alura.comex.model.Usuario;
import br.com.alura.comex.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Importação necessária
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioResponseDto criar(UsuarioCreateDto dto) {
        log.info("Criando usuário com email: {}", dto.getEmail());
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new UsuarioJaExisteException("Usuário com email '" + dto.getEmail() + "' já existe");
        }
        
        Usuario usuario = usuarioMapper.toEntity(dto);

        // 2. Criptografia da senha antes de salvar
        String senhaCriptografada = passwordEncoder.encode(dto.getSenha());
        usuario.setSenha(senhaCriptografada);

        Usuario salvo = usuarioRepository.save(usuario);
        log.info("Usuário criado com sucesso. ID: {}", salvo.getId());
        return usuarioMapper.toResponseDto(salvo);
    }

    public List<UsuarioResponseDto> listarTodos() {
        log.info("Listando todos os usuários");
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDto buscarPorId(Long id) {
        log.info("Buscando usuário por ID: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado com ID: " + id));
        return usuarioMapper.toResponseDto(usuario);
    }

    public void deletar(Long id) {
        log.info("Deletando usuário ID: {}", id);
        if (!usuarioRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Usuário não encontrado para deleção com ID: " + id);
        }
        usuarioRepository.deleteById(id);
        log.info("Usuário deletado com sucesso. ID: {}", id);
    }
}