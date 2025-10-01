package br.com.alura.comex.mapper;

import br.com.alura.comex.dto.CategoriaCreateDto;
import br.com.alura.comex.dto.CategoriaResponseDto;
import br.com.alura.comex.dto.CategoriaUpdateDto;
import br.com.alura.comex.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public Categoria toEntity(CategoriaCreateDto dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        return categoria;
    }

    public CategoriaResponseDto toResponseDto(Categoria categoria) {
        CategoriaResponseDto dto = new CategoriaResponseDto();
        dto.setId(categoria.getId());
        dto.setNome(categoria.getNome());
        dto.setAtivo(categoria.isAtivo());
        return dto;
    }
    
    public void updateEntity(Categoria categoria, CategoriaUpdateDto dto) {
        categoria.setNome(dto.getNome());
    }
}