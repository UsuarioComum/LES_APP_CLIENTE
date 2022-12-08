package com.example.cliente.viewModel;

import java.util.Map;

public class listaPedidos {
    String cliente, bairro, rua, valorTotal;
    Map<String, Object> lista;

    public listaPedidos() {
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valoTotal) {
        this.valorTotal = valoTotal;
    }
}
