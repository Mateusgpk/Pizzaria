package view;

import DAO.GerenciaPedidoPizza;
import model.Pedido;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaHistoricoPedidos extends JPanel {

    private final GerenciaPedidoPizza gerencia;
    private JTable tabelaHistorico;
    private DefaultTableModel modeloTabela;
    private JButton btnAtualizar;
    private JButton btnEditarPedido; // NOVO BOTÃO

    // NOVO: Recebe um Runnable para trocar de tela
    private final Runnable acaoTrocarParaPedidos;

    public TelaHistoricoPedidos(GerenciaPedidoPizza gerencia, Runnable acaoTrocarParaPedidos) {
        this.gerencia = gerencia;
        this.acaoTrocarParaPedidos = acaoTrocarParaPedidos;

        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(25, 25, 25, 25));

        add(criarPainelTabela(), BorderLayout.CENTER);
        add(criarPainelBotoes(), BorderLayout.SOUTH);
    }

    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Histórico de Pedidos Finalizados"));

        String[] colunas = {"Cliente", "Telefone", "Qtd. de Pizzas", "Valor Total (R$)"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };

        tabelaHistorico = new JTable(modeloTabela);
        tabelaHistorico.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaHistorico.getTableHeader().setReorderingAllowed(false);

        painel.add(new JScrollPane(tabelaHistorico), BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));

        btnAtualizar = new JButton("Atualizar Lista");
        btnAtualizar.addActionListener(e -> carregarDados());

        btnEditarPedido = new JButton("Alterar Pedido");
        btnEditarPedido.addActionListener(e -> acaoEditarPedido());

        painel.add(btnAtualizar);
        painel.add(btnEditarPedido);
        return painel;
    }

    private void acaoEditarPedido() {
        int linhaSelecionada = tabelaHistorico.getSelectedRow();

        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido na tabela para alterar.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Pega o pedido correspondente à linha
        List<Pedido> historico = gerencia.getHistoricoPedidos();
        Pedido pedidoSelecionado = historico.get(linhaSelecionada);

        // Joga o pedido de volta pro gerenciador
        gerencia.carregarPedidoParaEdicao(pedidoSelecionado);

        // Executa o gatilho que a JanelaPrincipal passou para trocar de tela
        acaoTrocarParaPedidos.run();
    }

    public void carregarDados() {
        modeloTabela.setRowCount(0);
        List<Pedido> historico = gerencia.getHistoricoPedidos();

        if (historico != null) {
            for (Pedido p : historico) {
                String nome = (p.getCliente() != null) ? p.getCliente().getNome() : "Desconhecido";
                String tel = (p.getCliente() != null) ? p.getCliente().getTelefone() : "-";

                modeloTabela.addRow(new Object[]{
                        nome,
                        tel,
                        p.getListaPizza().size(),
                        String.format("%.2f", p.getValorTotal())
                });
            }
        }
    }
}