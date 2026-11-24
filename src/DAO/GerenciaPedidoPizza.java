package DAO;

import model.*;
import model.Dao.PedidoDao;
import model.Dao.PizzaDao;

import java.util.ArrayList;
import java.util.List;

public class GerenciaPedidoPizza {

    private final PedidoDao pedidoDao;
    private final PizzaDao pizzaDao;

    private Cliente cliente = null;
    private Pedido pedido = new Pedido();

    public GerenciaPedidoPizza() {
        this.pedidoDao = new PedidoDao();
        this.pizzaDao = new PizzaDao();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    public List<Pedido> getHistoricoPedidos() throws Exception {
        List<Pedido> pedidos = pedidoDao.getAll();
        for (Pedido pedido1 : pedidos) {
            double valorSalvo = pedido1.getValorTotal();
            pedido1.setListaPizza(new ArrayList<>(pizzaDao.getAllPizzasPedido(pedido1.getId())));
            if (valorSalvo > 0) {
                pedido1.setValorTotal(valorSalvo);
            }
        }
        return pedidos;
    }

    public void adicionarPizza(Pizza pizza) {
        pedido.getListaPizza().add(pizza);
        calcularValorPedido();
    }

    public void calcularValorPedido() {
        pedido.setValorTotal(0);
        for (Pizza pizza : pedido.getListaPizza()) {
            pedido.setValorTotal(pedido.getValorTotal() + pizza.calculaPrecoInteiro());
        }
    }
    public void calcularValorPedido(Pedido pedido) {
        pedido.setValorTotal(0);
        for (Pizza pizza : pedido.getListaPizza()) {
            pedido.setValorTotal(pedido.getValorTotal() + pizza.calculaPrecoInteiro());
        }
    }

    public void atualizarStatusPedido(Pedido pedido, String novoStatus) throws Exception {
        pedido.setStatus(novoStatus);
        pedidoDao.update(pedido);
    }
    public Pizza fazerAdicionarPizza(Double lado, int formato, List<Sabores> sabores) {
        Forma forma = switch (formato) {
            case 1 -> new Circulo(lado);
            case 2 -> new Quadrado(lado);
            case 3 -> new Triangulo(lado);
            default -> throw new IllegalArgumentException("Formato inválido.");
        };

        Pizza nova = new Pizza(null, null, sabores, forma);
        nova.setTipoForma(formato);
        nova.setDimensao(lado);

        adicionarPizza(nova);
        return nova;
    }

    public void removePedido(Pedido pedido) throws Exception {
        pedidoDao.delete(pedido);
    }

    public void confirmarESalvarPedido() throws Exception {
        if (this.pedido == null || this.pedido.getListaPizza().isEmpty()) {
            throw new IllegalArgumentException("Adicione pelo menos uma pizza ao pedido.");
        }
        if (this.cliente == null || this.cliente.getId() == null) {
            throw new IllegalArgumentException("Selecione um cliente válido antes de confirmar o pedido.");
        }

        this.pedido.setCliente(this.cliente);
        calcularValorPedido();

        if (pedido.getId() == null) {
            pedidoDao.add(this.pedido);
        } else {
            pedidoDao.update(this.pedido);
            pizzaDao.deleteByIdPedido(this.pedido.getId());
        }

        for (Pizza pizza : this.pedido.getListaPizza()) {
            pizza.setIdPedido(this.pedido.getId());
            pizzaDao.add(pizza);
        }

        this.cliente = null;
        this.pedido = new Pedido();
    }

    public void carregarPedidoParaEdicao(Pedido pedidoAntigo) {
        this.pedido = pedidoAntigo;
        this.cliente = pedidoAntigo.getCliente();
    }

    public void carregarPedidoDoCliente(Cliente cliente) throws Exception {
        if (cliente == null) {
            this.cliente = null;
            this.pedido = new Pedido();
            return;
        }
        Pedido pedidoAberto = pedidoDao.getPedidoAbertoPorCliente(cliente.getId());
        if (pedidoAberto != null) {
            double valorSalvo = pedidoAberto.getValorTotal();
            pedidoAberto.setListaPizza(new ArrayList<>(pizzaDao.getAllPizzasPedido(pedidoAberto.getId())));
            if (valorSalvo > 0) {
                pedidoAberto.setValorTotal(valorSalvo);
            }
            this.pedido = pedidoAberto;
        } else {
            this.pedido = new Pedido();
            this.pedido.setCliente(cliente);
        }
        this.cliente = cliente;
    }

    public void removerPizzaPorIndice(int index) {
        if (this.pedido != null && index >= 0 && index < this.pedido.getListaPizza().size()) {
            this.pedido.getListaPizza().remove(index);
            calcularValorPedido();
        }
    }

    public void limparListaPizza() {
        this.pedido.getListaPizza().clear();
        calcularValorPedido();
    }

    public double calcularDimensaoPorArea(int formato, double area) {
        if (area < 100.0 || area > 1600.0) {
            throw new IllegalArgumentException("A área informada deve estar entre 100 e 1600 cm².");
        }

        return switch (formato) {
            case 1 -> Math.sqrt(area / Math.PI);
            case 2 -> Math.sqrt(area);
            case 3 -> Math.sqrt((4.0 * area) / Math.sqrt(3));
            default -> throw new IllegalArgumentException("Formato inválido.");
        };
    }
}