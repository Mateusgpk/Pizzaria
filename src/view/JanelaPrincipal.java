package view;

import DAO.GerenciadorCliente;
import controller.ClienteController;
import DAO.GerenciadorSabores;
import controller.PizzaController;
import model.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Janela principal do sistema
 *
 * @version 1.0
 */
public class JanelaPrincipal extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel painelCards;
    private JLabel lblTituloHeader;

    public JanelaPrincipal() {
        setTitle("Sistema de Pizzaria");
        setSize(1024, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        add(criarMenuLateral(), BorderLayout.WEST);

        JPanel painelDireito = new JPanel(new BorderLayout());

        painelDireito.add(criarHeader(), BorderLayout.NORTH);

        cardLayout = new CardLayout();
        painelCards = new JPanel(cardLayout);

        GerenciadorCliente daoCliente = new GerenciadorCliente();

        daoCliente.adicionarCliente(new Cliente("Nicolas", "Mendes Fernandes", "41998710753"));
        daoCliente.adicionarCliente(new Cliente("Mateus", "Gabriel Kuduavski", "41996441322"));
        daoCliente.adicionarCliente(new Cliente("Rafael", "Tsuji Uchida", "41988519427"));
        daoCliente.adicionarCliente(new Cliente("Carlos", "Eduardo de Moraes Vital", "41997253597"));

        TelaCliente telaClientes = new TelaCliente();
        ClienteController clienteController = new ClienteController(telaClientes, daoCliente);

        painelCards.add(telaClientes, "CLIENTES");

        GerenciadorSabores daoSabores = new GerenciadorSabores();
        TelaPizza telaPizzas = new TelaPizza(daoSabores.listarTipos());
        new PizzaController(telaPizzas, daoSabores);

        JPanel telaPedidos = new JPanel();
        telaPedidos.add(new JLabel("Conteúdo da Tela de Pedidos"));

        painelCards.add(telaClientes, "CLIENTES");
        painelCards.add(telaPizzas, "PIZZAS");
        painelCards.add(telaPedidos, "PEDIDOS");

        painelDireito.add(painelCards, BorderLayout.CENTER);

        add(painelDireito, BorderLayout.CENTER);
    }

    /**
     * Monta a estrutura em coluna do menu lateral.
     */
    private JPanel criarMenuLateral() {
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JLabel lblLogo = new JLabel("Pizzaria Express");
        lblLogo.setFont(new Font("Arial", Font.BOLD, 16));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        menu.add(lblLogo);
        menu.add(Box.createRigidArea(new Dimension(0, 30))); // Espaçamento vertical

        JButton btnClientes = criarBotaoMenu("Clientes", null);
        JButton btnPizzas = criarBotaoMenu("Pizzas", null);
        JButton btnPedidos = criarBotaoMenu("Pedidos", null);

        btnClientes.addActionListener((ActionEvent e) -> alternarTela("CLIENTES", "Clientes do Sistema"));
        btnPizzas.addActionListener((ActionEvent e) -> alternarTela("PIZZAS", "Cadastro de Sabores e Preços"));
        btnPedidos.addActionListener((ActionEvent e) -> alternarTela("PEDIDOS", "Painel de Pedidos e Entregas"));

        menu.add(btnClientes);
        menu.add(Box.createRigidArea(new Dimension(0, 10)));
        menu.add(btnPizzas);
        menu.add(Box.createRigidArea(new Dimension(0, 10)));
        menu.add(btnPedidos);

        return menu;
    }

    /**
     * Auxiliar para padronizar o visual dos botões do menu lateral.
     */
    private JButton criarBotaoMenu(String texto, Icon icone) {
        JButton botao = new JButton(texto, icone);
        botao.setMaximumSize(new Dimension(180, 40));
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.setFocusable(false);
        return botao;
    }

    /**
     * Monta o painel de cabeçalho descritivo superior.
     */
    private JPanel criarHeader() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        lblTituloHeader = new JLabel("Clientes do Sistema");
        lblTituloHeader.setFont(new Font("Arial", Font.BOLD, 22));

        header.add(lblTituloHeader);
        return header;
    }

    /**
     * Executa a transição polimórfica/estrutural de telas e altera o texto do header.
     */
    private void alternarTela(String idCard, String novoTituloHeader) {
        cardLayout.show(painelCards, idCard);
        lblTituloHeader.setText(novoTituloHeader);
    }
}