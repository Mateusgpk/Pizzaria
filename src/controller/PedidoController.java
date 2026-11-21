package controller;

import DAO.GerenciadorCliente;
import model.Cliente;
import view.TelaPedido;

import javax.swing.*;
import java.util.List;

public class PedidoController {

    private final TelaPedido view;
    private final GerenciadorCliente daoCliente;

    public PedidoController(TelaPedido view, GerenciadorCliente daoCliente) {
        this.view = view;
        this.daoCliente = daoCliente;
        inicializarEventos();
    }

    private void inicializarEventos() {
        view.getBtnBuscarCliente().addActionListener(e -> acaoBuscarCliente());
    }

    private void acaoBuscarCliente() {
        String termo = view.getTxtBuscaCliente().getText().trim();

        if (termo.isEmpty()) {
            view.exibirClienteEncontrado(null);
            return;
        }

        try {
            List<Cliente> todos = daoCliente.listarTodos();
            Cliente encontrado = null;

            String termoMinusculo = termo.toLowerCase();
            String termoSomenteNumeros = termo.replaceAll("\\D", "");

            for (Cliente c : todos) {
                String nomeCompleto = (c.getNome() + " " + c.getSobrenome()).toLowerCase();
                String telefoneLimpo = c.getTelefone().replaceAll("\\D", "");

                boolean bateNome = nomeCompleto.contains(termoMinusculo);
                boolean bateTelefone = !termoSomenteNumeros.isEmpty() && telefoneLimpo.contains(termoSomenteNumeros);

                if (bateNome || bateTelefone) {
                    encontrado = c;
                    break;
                }
            }

            view.exibirClienteEncontrado(encontrado);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erro ao buscar cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}