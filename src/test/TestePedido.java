package test;

import DAO.GerenciadorCliente;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class TestePedido {
    public static void main(String[] args) {

        GerenciadorCliente gerenciador = new GerenciadorCliente();

        System.out.println("Cadastrando clientes válidos...");
        gerenciador.adicionarCliente(new Cliente("Nicolas", "Mendes", "41999999999"));

        TipoSabor normal = new TipoSabor("normal",0.40);
        Sabores calabresa= new Sabores(normal,"Calabresa");
        List<Sabores> sabores= new ArrayList<>();
        sabores.add(calabresa);
        Pizza primeira = new Quadrado(20,sabores);
        ArrayList<Pizza> pizzas = new ArrayList<>();
        pizzas.add(primeira);
        Pedido pedido = new Pedido(gerenciador.buscarPorTelefone("41999999999"));
        pedido.setListaPizza(pizzas);

    }
}
