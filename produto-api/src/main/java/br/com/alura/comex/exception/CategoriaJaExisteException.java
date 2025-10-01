package br.com.alura.comex.exception;

public class CategoriaJaExisteException extends RuntimeException {
    public CategoriaJaExisteException(String message) {
        super(message);
    }
}