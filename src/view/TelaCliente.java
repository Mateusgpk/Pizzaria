package view;

import util.mascaraTelefone;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Tela de Controle e Cadastro de clientes.
 *
 * @version 1.0
 */
public class TelaCliente extends JPanel {

    // Componentes do Formulário
    private JTextField txtNome;
    private JTextField txtSobrenome;
    private JTextField txtTelefone;

    // Componentes de Pesquisa e Tabela
    private JTextField txtPesquisa;
    private JTable tabelaClientes;
    private DefaultTableModel modeloTabela;

    // Botões de Ação
    private JButton btnSalvar;
    private JButton btnAtualizar;
    private JButton btnExcluir;
    private JButton btnLimpar;

    public TelaCliente() {
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(25, 25, 25, 25));

        add(criarPainelFormulario(), BorderLayout.NORTH);

        add(criarPainelTabela(), BorderLayout.CENTER);

        add(criarPainelBotoes(), BorderLayout.SOUTH);
    }

    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Informações Pessoais"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        painel.add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        txtNome = new JTextField();
        painel.add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        painel.add(new JLabel("Sobrenome:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        txtSobrenome = new JTextField();
        painel.add(txtSobrenome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        painel.add(new JLabel("Telefone:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        txtTelefone = new JTextField();
        painel.add(txtTelefone, gbc);

        return painel;
    }

    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout(0, 12));

        JPanel painelBusca = new JPanel(new BorderLayout(10, 0));
        painelBusca.add(new JLabel("Filtrar por Sobrenome/Telefone:"), BorderLayout.WEST);
        txtPesquisa = new JTextField();
        painelBusca.add(txtPesquisa, BorderLayout.CENTER);
        painel.add(painelBusca, BorderLayout.NORTH);

        String[] colunas = {"Nome", "Sobrenome", "Telefone"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaClientes = new JTable(modeloTabela);
        tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaClientes.getTableHeader().setReorderingAllowed(false);

        tabelaClientes.getColumnModel().getColumn(2).setCellRenderer(new mascaraTelefone());

        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        painel.add(scrollPane, BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));

        btnLimpar = new JButton("Limpar Campos");
        btnExcluir = new JButton("Excluir Cliente");
        btnAtualizar = new JButton("Atualizar Dados");
        btnSalvar = new JButton("Salvar Cadastro");

        btnSalvar.putClientProperty("JButton.buttonType", "accent");

        painel.add(btnLimpar);
        painel.add(btnExcluir);
        painel.add(btnAtualizar);
        painel.add(btnSalvar);

        return painel;
    }

    public JTextField getTxtNome() {
        return txtNome;
    }

    public JTextField getTxtSobrenome() {
        return txtSobrenome;
    }

    public JTextField getTxtTelefone() {
        return txtTelefone;
    }

    public JTextField getTxtPesquisa() {
        return txtPesquisa;
    }

    public JTable getTabelaClientes() {
        return tabelaClientes;
    }

    public DefaultTableModel getModeloTabela() {
        return modeloTabela;
    }

    public JButton getBtnSalvar() {
        return btnSalvar;
    }

    public JButton getBtnAtualizar() {
        return btnAtualizar;
    }

    public JButton getBtnExcluir() {
        return btnExcluir;
    }

    public JButton getBtnLimpar() {
        return btnLimpar;
    }
}
