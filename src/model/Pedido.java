package model;

import java.util.ArrayList;

public class Pedido {
    public Long getId() {
        return id;
    }

    private Long id;
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

    public Pedido(Long id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
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
        recalcularValorTotal();
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void recalcularValorTotal() {
        double total = 0.0;
        if (this.listaPizza != null) {
            for (Pizza pizza : this.listaPizza) {
                total += pizza.calculaPrecoInteiro();
            }
        }
        this.valorTotal = total;
    }
    public void setId(Long i) {
        this.id = i;
    }
}