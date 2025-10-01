package br.com.alura.comex.service;

import br.com.alura.comex.exception.CategoriaJaExisteException;
import br.com.alura.comex.exception.EntidadeNaoEncontradaException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestaExcecoesNegocio {

    @Test
    void testaEntidadeNaoEncontradaException() {
        String mensagem = "Entidade não encontrada com ID: 999";
        
        EntidadeNaoEncontradaException exception = assertThrows(
            EntidadeNaoEncontradaException.class, 
            () -> {
                throw new EntidadeNaoEncontradaException(mensagem);
            }
        );
        
        assertEquals(mensagem, exception.getMessage());
        assertNotNull(exception);
    }

    @Test
    void testaCategoriaJaExisteException() {
        String mensagem = "Categoria com nome 'Eletrônicos' já existe";
        
        CategoriaJaExisteException exception = assertThrows(
            CategoriaJaExisteException.class, 
            () -> {
                throw new CategoriaJaExisteException(mensagem);
            }
        );
        
        assertEquals(mensagem, exception.getMessage());
        assertNotNull(exception);
    }

    @Test
    void testaHerancaDeRuntimeException() {
        EntidadeNaoEncontradaException entidadeException = new EntidadeNaoEncontradaException("Teste");
        CategoriaJaExisteException categoriaException = new CategoriaJaExisteException("Teste");
        
        assertTrue(entidadeException instanceof RuntimeException);
        assertTrue(categoriaException instanceof RuntimeException);
    }
}