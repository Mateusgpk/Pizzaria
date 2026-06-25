package model;

import java.util.ArrayList;

public class Pedido {
    public int getId() {
        return id;
    }

    private static int idatual;
    private int id;
    private Cliente cliente;
    private ArrayList<Pizza> listaPizza;
    private double valorTotal;
    private String status = "Aberto";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Pedido() {
        listaPizza = new ArrayList<>();
    }
    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.id=idatual;
        idatual++;
        this.listaPizza = new ArrayList<>();
        this.valorTotal = 0.0;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<Pizza> getListaPizza() {
        return listaPizza;
    }

    public void setListaPizza(ArrayList<Pizza> listaPizza) {
        this.listaPizza = listaPizza;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }


}