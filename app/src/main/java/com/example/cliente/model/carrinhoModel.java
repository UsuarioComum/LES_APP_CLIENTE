package com.example.cliente.model;

public class carrinhoModel {

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    String imagem;
    String nome, sabor, valor;

    public carrinhoModel(String imagem, String nome, String sabor, String valor) {
        this.imagem = imagem;
        this.nome = nome;
        this.sabor = sabor;
        this.valor = valor;
    }
}
