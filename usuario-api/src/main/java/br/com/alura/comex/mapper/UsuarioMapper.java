package br.com.alura.comex.mapper;

import br.com.alura.comex.dto.UsuarioCreateDto;
import br.com.alura.comex.dto.UsuarioResponseDto;
import br.com.alura.comex.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioCreateDto dto) {
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        return usuario;
    }

    public UsuarioResponseDto toResponseDto(Usuario usuario) {
        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setId(usuario.getId());
        dto.setEmail(usuario.getEmail());
        return dto;
    }
}