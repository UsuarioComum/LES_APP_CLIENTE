package com.example.cliente.model;

public class Usuarios {
    String nome, cpf, email, rua, bairro, numero, valorTotal;

    public Usuarios() {
    }
    public Usuarios(String nome, String cpf, String email, String rua, String bairro, String numero, String valorTotal) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.rua = rua;
        this.bairro = bairro;
        this.numero = numero;
        this.valorTotal = valorTotal;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

}
