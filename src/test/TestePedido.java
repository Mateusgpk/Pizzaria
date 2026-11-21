package test;

import DAO.GerenciadorCliente;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class TestePedido {
    public static void main(String[] args) throws Exception {

        GerenciadorCliente gerenciador = new GerenciadorCliente();

        System.out.println("Cadastrando clientes válidos...");
        gerenciador.adicionarCliente(new Cliente(1L,"Nicolas", "Mendes", "41999999999"));

        TipoSabor normal = new TipoSabor(1L,"normal",0.40);
        Sabores calabresa= new Sabores(1L,normal,"Calabresa");
        List<Sabores> sabores= new ArrayList<>();
        sabores.add(calabresa);
        Pizza primeira = new Pizza(1L,1L,sabores,new Quadrado(20));
        ArrayList<Pizza> pizzas = new ArrayList<>();
        pizzas.add(primeira);
        Pedido pedido = new Pedido(1L,gerenciador.buscarPorTelefone("41999999999"));
        pedido.setListaPizza(pizzas);

    }
}
