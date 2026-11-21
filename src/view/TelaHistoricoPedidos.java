package view;

import DAO.GerenciaPedidoPizza;
import model.Pedido;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class TelaHistoricoPedidos extends JPanel {

    private final GerenciaPedidoPizza gerencia;
    private JTable tabelaHistorico;
    private DefaultTableModel modeloTabela;
    private JButton btnDeletar;
    private JButton btnAtualizar;
    private JButton btnEditarPedido;


    private List<Pedido> historicoAtual;
    private final Runnable acaoTrocarParaPedidos;

    public TelaHistoricoPedidos(GerenciaPedidoPizza gerencia, Runnable acaoTrocarParaPedidos) {
        this.gerencia = gerencia;
        this.acaoTrocarParaPedidos = acaoTrocarParaPedidos;

        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(25, 25, 25, 25));

        add(criarPainelTabela(), BorderLayout.CENTER);
        add(criarPainelBotoes(), BorderLayout.SOUTH);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                try {
                    carregarDados();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }

    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Histórico de Pedidos Finalizados"));

        // 1. Adicionamos a coluna "Status" no final
        String[] colunas = {"id", "Cliente", "Telefone", "Qtd. de Pizzas", "Valor Total (R$)", "Status"};

        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                // 2. Libera a edição APENAS para a coluna do Status (índice 4)
                return col == 5;
            }

            @Override
            public void setValueAt(Object aValue, int row, int column) {
                super.setValueAt(aValue, row, column);
                if (column == 5 && historicoAtual != null && row < historicoAtual.size()) {
                    Pedido p = historicoAtual.get(row);
                    try {
                        gerencia.atualizarStatusPedido(p, aValue.toString());
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(
                                TelaHistoricoPedidos.this,
                                "Erro ao atualizar status: " + e.getMessage(),
                                "Erro",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        };

        tabelaHistorico = new JTable(modeloTabela);
        tabelaHistorico.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaHistorico.getTableHeader().setReorderingAllowed(false);

        // 4. MÁGICA DO SWING: Transforma as células da coluna 4 em JComboBox
        JComboBox<String> comboStatus = new JComboBox<>(new String[]{"Aberto", "A Caminho", "Entregue"});
        tabelaHistorico.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(comboStatus));

        painel.add(new JScrollPane(tabelaHistorico), BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));

        btnDeletar = new JButton("Deletar Pedido");
        btnDeletar.addActionListener(e -> {
            try {
                deletarPedido();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        btnAtualizar = new JButton("Atualizar Lista");
        btnAtualizar.addActionListener(e -> {
            try {
                carregarDados();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        btnEditarPedido = new JButton("Alterar Pedido");
        btnEditarPedido.addActionListener(e -> {
            try {
                acaoEditarPedido();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        painel.add(btnDeletar);
        painel.add(btnAtualizar);
        painel.add(btnEditarPedido);
        return painel;
    }

    private void deletarPedido() throws Exception {
        int linhaSelecionada = tabelaHistorico.getSelectedRow();
        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido na tabela para deletar.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        List<Pedido> historico = gerencia.getHistoricoPedidos();
        Pedido pedidoSelecionado = historico.get(linhaSelecionada);
        gerencia.removePedido(pedidoSelecionado);
        carregarDados();

    }

    private void acaoEditarPedido() throws Exception {
        int linhaSelecionada = tabelaHistorico.getSelectedRow();

        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido na tabela para alterar.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Pega o pedido correspondente à linha
        List<Pedido> historico = gerencia.getHistoricoPedidos();
        Pedido pedidoSelecionado = historico.get(linhaSelecionada);


        if (!pedidoSelecionado.getStatus().equals("Aberto")) {
            JOptionPane.showMessageDialog(this,
                    "Você não pode alterar um pedido que já está '" + pedidoSelecionado.getStatus() + "'.",
                    "Ação Bloqueada",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        gerencia.carregarPedidoParaEdicao(pedidoSelecionado);
        acaoTrocarParaPedidos.run();
    }

    public void carregarDados() throws Exception {
        if (tabelaHistorico.isEditing()) {
            tabelaHistorico.getCellEditor().stopCellEditing();
        }

        modeloTabela.setRowCount(0);
        this.historicoAtual = gerencia.getHistoricoPedidos();

        if (this.historicoAtual != null) {
            for (Pedido p : this.historicoAtual) {
                String id = String.valueOf(p.getId());
                String nome = (p.getCliente() != null) ? p.getCliente().getNome() : "Desconhecido";
                String tel = (p.getCliente() != null) ? p.getCliente().getTelefone() : "-";

                modeloTabela.addRow(new Object[]{
                        id,
                        nome,
                        tel,
                        p.getListaPizza().size(),
                        String.format("%.2f", p.getValorTotal()),
                        p.getStatus()
                });
            }
        }
    }
}