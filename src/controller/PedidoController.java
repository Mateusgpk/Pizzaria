package controller;

import DAO.GerenciadorCliente;
import model.Cliente;
import view.TelaPedido;

import java.util.List;

/**
 * Controlador da TelaPedido.
 * Segue o mesmo padrão do ClienteController e PizzaController.
 *
 * @version 1.0
 */
public class PedidoController {

    private final TelaPedido      view;
    private final GerenciadorCliente daoCliente;

    public PedidoController(TelaPedido view, GerenciadorCliente daoCliente) {
        this.view      = view;
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

        List<Cliente> todos = daoCliente.listarTodos();
        Cliente encontrado = null;

        // Prepara os termos fora do loop para otimizar
        String termoMinusculo = termo.toLowerCase();
        String termoSomenteNumeros = termo.replaceAll("\\D", "");

        for (Cliente c : todos) {
            String nomeCompleto = (c.getNome() + " " + c.getSobrenome()).toLowerCase();
            String telefoneLimpo = c.getTelefone().replaceAll("\\D", "");

            // Verifica se o termo bate com alguma parte do nome completo
            boolean bateNome = nomeCompleto.contains(termoMinusculo);

            // Só verifica o telefone se o usuário digitou algum número na busca
            boolean bateTelefone = !termoSomenteNumeros.isEmpty() && telefoneLimpo.contains(termoSomenteNumeros);

            if (bateNome || bateTelefone) {
                encontrado = c;
                break;
            }
        }

        view.exibirClienteEncontrado(encontrado);
    }
}