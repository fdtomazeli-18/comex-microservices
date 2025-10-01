package br.com.alura.comex.service;

import br.com.alura.comex.dto.CategoriaCreateDto;
import br.com.alura.comex.dto.CategoriaResponseDto;
import br.com.alura.comex.exception.CategoriaJaExisteException;
import br.com.alura.comex.exception.EntidadeNaoEncontradaException;
import br.com.alura.comex.mapper.CategoriaMapper;
import br.com.alura.comex.model.Categoria;
import br.com.alura.comex.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestaCategoriaService {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private CategoriaMapper categoriaMapper;

    @InjectMocks
    private CategoriaService categoriaService;

    private CategoriaCreateDto categoriaCreateDto;
    private Categoria categoria;
    private CategoriaResponseDto categoriaResponseDto;

    @BeforeEach
    void setUp() {
        categoriaCreateDto = new CategoriaCreateDto();
        categoriaCreateDto.setNome("Eletrônicos");

        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Eletrônicos");
        categoria.setAtivo(true);

        categoriaResponseDto = new CategoriaResponseDto();
        categoriaResponseDto.setId(1L);
        categoriaResponseDto.setNome("Eletrônicos");
        categoriaResponseDto.setAtivo(true);
    }

    @Test
    void deveCriarCategoriaComSucesso() {
        when(categoriaRepository.existsByNomeAndAtivo(anyString(), anyBoolean())).thenReturn(false);
        when(categoriaMapper.toEntity(any(CategoriaCreateDto.class))).thenReturn(categoria);
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);
        when(categoriaMapper.toResponseDto(any(Categoria.class))).thenReturn(categoriaResponseDto);

        CategoriaResponseDto resultado = categoriaService.criar(categoriaCreateDto);

        assertNotNull(resultado);
        assertEquals("Eletrônicos", resultado.getNome());
        verify(categoriaRepository).save(any(Categoria.class));
    }

    @Test
    void deveLancarExcecaoQuandoCategoriaJaExiste() {
        when(categoriaRepository.existsByNomeAndAtivo(anyString(), anyBoolean())).thenReturn(true);

        assertThrows(CategoriaJaExisteException.class, () -> {
            categoriaService.criar(categoriaCreateDto);
        });

        verify(categoriaRepository, never()).save(any(Categoria.class));
    }

    @Test
    void deveBuscarCategoriaPorId() {
        when(categoriaRepository.findByIdAndAtivo(anyLong(), anyBoolean())).thenReturn(Optional.of(categoria));
        when(categoriaMapper.toResponseDto(any(Categoria.class))).thenReturn(categoriaResponseDto);

        CategoriaResponseDto resultado = categoriaService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Eletrônicos", resultado.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoCategoriaNaoEncontrada() {
        when(categoriaRepository.findByIdAndAtivo(anyLong(), anyBoolean())).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> {
            categoriaService.buscarPorId(999L);
        });
    }
}