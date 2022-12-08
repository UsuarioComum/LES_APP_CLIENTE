package com.example.cliente.model;

public class Produtos {

    String nome, sabor, valor, imagem;

    public Produtos() {
    }

    public Produtos(String nome, String sabor, String valor, String imagem) {
        this.nome = nome;
        this.sabor = sabor;
        this.valor = valor;
        this.imagem = imagem;
    }

    // Até o momento foi necessário utilizar somente os getters
    public String getNome() {
        return nome;
    }

    public String getSabor() {
        return sabor;
    }

    public String getValor() {
        return valor;
    }

    public String getImagem() {
        return imagem;
    }
}
