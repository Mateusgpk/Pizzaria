package view;

import model.Sabores;
import model.TipoSabor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Tela de Cadastro de Sabores e Preços por Tipo.
 */
public class TelaPizza extends JPanel {

    // Aba Sabores
    private JTextField txtNomeSabor;
    private JComboBox<TipoSabor> cmbTipoSabor;
    private JTable tabelaSabores;
    private DefaultTableModel modeloTabelaSabores;
    private JButton btnSalvarSabor, btnAtualizarSabor, btnExcluirSabor, btnLimparSabor;

    // Aba Preços
    private JTable tabelaPrecos;
    private DefaultTableModel modeloTabelaPrecos;
    private JButton btnSalvarPrecos;

    public TelaPizza(List<TipoSabor> tipos) {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(25, 25, 25, 25));

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Sabores", criarAbaSabores(tipos));
        abas.addTab("Preços por Tipo", criarAbaPrecos(tipos));
        add(abas, BorderLayout.CENTER);
    }

    private JPanel criarAbaSabores(List<TipoSabor> tipos) {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Formulário
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Cadastro de Sabor"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNomeSabor = new JTextField();
        cmbTipoSabor = new JComboBox<>(tipos.toArray(new TipoSabor[0]));

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0; form.add(new JLabel("Nome do Sabor:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; form.add(txtNomeSabor, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; form.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; form.add(cmbTipoSabor, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        btnLimparSabor   = new JButton("Limpar Campos");
        btnExcluirSabor  = new JButton("Excluir Sabor");
        btnAtualizarSabor = new JButton("Atualizar Dados");
        btnSalvarSabor   = new JButton("Salvar Sabor");
        btnSalvarSabor.putClientProperty("JButton.buttonType", "accent");
        painelBotoes.add(btnLimparSabor);
        painelBotoes.add(btnExcluirSabor);
        painelBotoes.add(btnAtualizarSabor);
        painelBotoes.add(btnSalvarSabor);

        // Tabela
        modeloTabelaSabores = new DefaultTableModel(new String[]{"Nome do Sabor", "Tipo"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaSabores = new JTable(modeloTabelaSabores);
        tabelaSabores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaSabores.getTableHeader().setReorderingAllowed(false);

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(painelBotoes, BorderLayout.SOUTH);

        painel.add(top, BorderLayout.NORTH);
        painel.add(new JScrollPane(tabelaSabores), BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarAbaPrecos(List<TipoSabor> tipos) {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lbl = new JLabel("Edite o preço por cm² de cada tipo e clique em Salvar:");
        lbl.setFont(new Font("Arial", Font.PLAIN, 14));

        modeloTabelaPrecos = new DefaultTableModel(new String[]{"Tipo de Pizza", "Preço por cm² (R$)"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return c == 1; }
        };
        tabelaPrecos = new JTable(modeloTabelaPrecos);
        tabelaPrecos.setRowHeight(30);
        for (TipoSabor t : tipos)
            modeloTabelaPrecos.addRow(new Object[]{t.getNome(), String.format("%.2f", t.getPreco())});

        btnSalvarPrecos = new JButton("Salvar Preços");
        btnSalvarPrecos.putClientProperty("JButton.buttonType", "accent");

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoes.add(btnSalvarPrecos);

        painel.add(lbl, BorderLayout.NORTH);
        painel.add(new JScrollPane(tabelaPrecos), BorderLayout.CENTER);
        painel.add(botoes, BorderLayout.SOUTH);
        return painel;
    }

    // Getters para o Controller
    public JTextField getTxtNomeSabor() { return txtNomeSabor; }
    public JComboBox<TipoSabor> getCmbTipoSabor() { return cmbTipoSabor; }
    public JTable getTabelaSabores() { return tabelaSabores; }
    public DefaultTableModel getModeloTabelaSabores() { return modeloTabelaSabores; }
    public JButton getBtnSalvarSabor() { return btnSalvarSabor; }
    public JButton getBtnAtualizarSabor() { return btnAtualizarSabor; }
    public JButton getBtnExcluirSabor() { return btnExcluirSabor; }
    public JButton getBtnLimparSabor() { return btnLimparSabor; }
    public JTable getTabelaPrecos() { return tabelaPrecos; }
    public DefaultTableModel getModeloTabelaPrecos() { return modeloTabelaPrecos; }
    public JButton getBtnSalvarPrecos() { return btnSalvarPrecos; }
}
