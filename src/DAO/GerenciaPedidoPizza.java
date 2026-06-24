package DAO;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class GerenciaPedidoPizza {

    // ── HISTÓRICO GERAL (Todos os pedidos confirmados) ──────────
    private final List<Pedido> historicoPedidos = new ArrayList<>();

    // ── PEDIDO ATUAL (A venda que está acontecendo agora na tela) ──
    private Cliente cliente = null;
    private Pedido pedido = new Pedido(cliente);

    // ── GETTERS & SETTERS ─────────────────────────────────────────
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        // Importante: atualiza o cliente dentro do pedido atual também
        if (this.pedido != null) {
            this.pedido.setCliente(cliente);
        }
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    /**
     * Retorna a lista de todos os pedidos já finalizados no sistema.
     */
    public List<Pedido> getHistoricoPedidos() {
        return new ArrayList<>(this.historicoPedidos);
    }

    // ── LÓGICA DE NEGÓCIO ─────────────────────────────────────────

    public void adicionarPizza(Pizza pizza){
        pedido.getListaPizza().add(pizza);
        calcularValorPedido();
    }

    public void calcularValorPedido(){
        pedido.setValorTotal(0);
        for(Pizza pizza : pedido.getListaPizza()){
            pedido.setValorTotal(pedido.getValorTotal() + pizza.calculaPrecoInteiro());
        }
    }

    public void fazerAdicionarPizza(Double lado, int formato, List<Sabores> sabores) {
        try {
            Pizza nova = switch (formato) {
                case 1 -> new Circulo(lado, sabores);
                case 2 -> new Quadrado(lado, sabores);
                case 3 -> new Triangulo(lado, sabores);
                default -> null;
            };
            adicionarPizza(nova);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Salva o pedido atual no histórico e limpa o "carrinho" para a próxima venda.
     */
    public void confirmarESalvarPedido() {
        if (this.pedido != null && !this.pedido.getListaPizza().isEmpty()) {
            // 1. Salva na lista global
            this.historicoPedidos.add(this.pedido);

            // 2. Reseta o estado para o próximo pedido não puxar dados do anterior
            this.cliente = null;
            this.pedido = new Pedido(null);
        }
    }
}