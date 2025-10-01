package br.com.alura.comex.service;

import br.com.alura.comex.dto.ProdutoCreateDto;
import br.com.alura.comex.dto.ProdutoResponseDto;
import br.com.alura.comex.exception.EntidadeNaoEncontradaException;
import br.com.alura.comex.mapper.ProdutoMapper;
import br.com.alura.comex.model.Categoria;
import br.com.alura.comex.model.Produto;
import br.com.alura.comex.repository.CategoriaRepository;
import br.com.alura.comex.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestaProdutoService {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ProdutoMapper produtoMapper;

    @InjectMocks
    private ProdutoService produtoService;

    private ProdutoCreateDto produtoCreateDto;
    private Produto produto;
    private Categoria categoria;
    private ProdutoResponseDto produtoResponseDto;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Eletrônicos");
        categoria.setAtivo(true);

        produtoCreateDto = new ProdutoCreateDto();
        produtoCreateDto.setNome("Smartphone");
        produtoCreateDto.setDescricao("Smartphone Android");
        produtoCreateDto.setPreco(new BigDecimal("1500.00"));
        produtoCreateDto.setQuantidadeEstoque(10);
        produtoCreateDto.setCategoriaId(1L);

        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Smartphone");
        produto.setDescricao("Smartphone Android");
        produto.setPreco(new BigDecimal("1500.00"));
        produto.setQuantidadeEstoque(10);
        produto.setCategoria(categoria);
        produto.setAtivo(true);

        produtoResponseDto = new ProdutoResponseDto();
        produtoResponseDto.setId(1L);
        produtoResponseDto.setNome("Smartphone");
        produtoResponseDto.setPreco(new BigDecimal("1500.00"));
    }

    @Test
    void deveCriarProdutoComSucesso() {
        when(categoriaRepository.findByIdAndAtivo(anyLong(), anyBoolean())).thenReturn(Optional.of(categoria));
        when(produtoMapper.toEntity(any(ProdutoCreateDto.class), any(Categoria.class))).thenReturn(produto);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);
        when(produtoMapper.toResponseDto(any(Produto.class))).thenReturn(produtoResponseDto);

        ProdutoResponseDto resultado = produtoService.criar(produtoCreateDto);

        assertNotNull(resultado);
        assertEquals("Smartphone", resultado.getNome());
        verify(produtoRepository).save(any(Produto.class));
    }

    @Test
    void deveLancarExcecaoQuandoCategoriaNaoEncontrada() {
        when(categoriaRepository.findByIdAndAtivo(anyLong(), anyBoolean())).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> {
            produtoService.criar(produtoCreateDto);
        });

        verify(produtoRepository, never()).save(any(Produto.class));
    }

    @Test
    void deveBuscarProdutoPorId() {
        when(produtoRepository.findByIdAndAtivo(anyLong(), anyBoolean())).thenReturn(Optional.of(produto));
        when(produtoMapper.toResponseDto(any(Produto.class))).thenReturn(produtoResponseDto);

        ProdutoResponseDto resultado = produtoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Smartphone", resultado.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontrado() {
        when(produtoRepository.findByIdAndAtivo(anyLong(), anyBoolean())).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> {
            produtoService.buscarPorId(999L);
        });
    }
}