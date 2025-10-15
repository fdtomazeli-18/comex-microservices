package br.com.alura.produto.events;

public class CategoriaEvent {
    private String nome;

    public CategoriaEvent() {}

    public CategoriaEvent(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}