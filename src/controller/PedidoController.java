package controller;

import DAO.GerenciaPedidoPizza;
import DAO.GerenciadorCliente;
import model.Cliente;
import view.TelaPedido;

import javax.swing.*;

public class PedidoController {

    private final TelaPedido view;
    private final GerenciadorCliente daoCliente;
    private final GerenciaPedidoPizza gerenciaPedido;

    public PedidoController(TelaPedido view, GerenciadorCliente daoCliente, GerenciaPedidoPizza gerenciaPedido) {
        this.view = view;
        this.daoCliente = daoCliente;
        this.gerenciaPedido = gerenciaPedido;
        inicializarEventos();
    }

    private void inicializarEventos() {
        view.getBtnBuscarCliente().addActionListener(e -> acaoBuscarCliente());
    }

    private void acaoBuscarCliente() {
        String telefone = view.getTxtBuscaCliente().getText().replaceAll("\\D", "");

        if (telefone.isEmpty()) {
            try {
                gerenciaPedido.carregarPedidoDoCliente(null);
            } catch (Exception ignored) {
            }
            view.carregarDadosDoPedidoAtual();
            return;
        }

        try {
            Cliente encontrado = daoCliente.buscarPorTelefone(telefone);
            if (encontrado == null) {
                for (Cliente c : daoCliente.listarTodos()) {
                    if (c.getTelefone().contains(telefone)) {
                        encontrado = c;
                        break;
                    }
                }
            }

            gerenciaPedido.carregarPedidoDoCliente(encontrado);
            view.carregarDadosDoPedidoAtual();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erro ao buscar cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}