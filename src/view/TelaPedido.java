package view;

import DAO.GerenciaPedidoPizza;
import DAO.GerenciadorSabores;
import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Tela de realização de pedidos de pizza.
 * Busca um cliente pelo nome ou telefone, depois permite
 * adicionar até 2 sabores por pizza e confirmar o pedido.
 *
 * @version 1.1
 */
public class TelaPedido extends JPanel {

    // ── Painel de busca de cliente ────────────────────────────────────────────
    private JTextField txtBuscaCliente;
    private JButton    btnBuscarCliente;
    private JLabel     lblClienteSelecionado;

    // ── Formulário da pizza ───────────────────────────────────────────────────
    private JComboBox<TipoSabor> cbTipo1;
    private JComboBox<Sabores>   cbSabor1;
    private JCheckBox            chkSabor2;
    private JComboBox<TipoSabor> cbTipo2;
    private JComboBox<Sabores>   cbSabor2;
    private JComboBox<String>    cbFormato;
    private JTextField           txtTamanho;

    // ── Tabela de pizzas adicionadas ──────────────────────────────────────────
    private JTable            tabelaPizzas;
    private DefaultTableModel modeloTabela;

    // ── Botões ────────────────────────────────────────────────────────────────
    private JButton btnAdicionarPizza;
    private JButton btnRemoverPizza;
    private JButton btnConfirmarPedido;
    private JButton btnLimpar;

    // ── Dependências ──────────────────────────────────────────────────────────
    private final GerenciaPedidoPizza gerencia;
    private final GerenciadorSabores  gerenciadorSabores;

    public TelaPedido(GerenciadorSabores gerenciadorSabores, GerenciaPedidoPizza gerenciaPedidoPizza) {
        this.gerenciadorSabores = gerenciadorSabores;
        this.gerencia=gerenciaPedidoPizza;

        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(25, 25, 25, 25));

        add(criarPainelCliente(),  BorderLayout.NORTH);
        add(criarPainelCentro(),   BorderLayout.CENTER);
        add(criarPainelBotoes(),   BorderLayout.SOUTH);

        recarregarSabores(); // popula combos com dados do GerenciadorSabores
        atualizarEstadoSabor2();
        atualizarEstadoFormulario(false);
    }

    // ── Painel de busca de cliente ────────────────────────────────────────────
    private JPanel criarPainelCliente() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Cliente"));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.fill   = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0; g.weightx = 0;
        painel.add(new JLabel("Buscar por nome ou telefone:"), g);

        g.gridx = 1; g.weightx = 1.0;
        txtBuscaCliente = new JTextField();
        painel.add(txtBuscaCliente, g);

        g.gridx = 2; g.weightx = 0;
        btnBuscarCliente = new JButton("Buscar");
        painel.add(btnBuscarCliente, g);

        g.gridx = 0; g.gridy = 1; g.gridwidth = 3; g.weightx = 1.0;
        lblClienteSelecionado = new JLabel("Nenhum cliente selecionado.");
        lblClienteSelecionado.setForeground(Color.GRAY);
        lblClienteSelecionado.setFont(lblClienteSelecionado.getFont().deriveFont(Font.ITALIC));
        painel.add(lblClienteSelecionado, g);

        return painel;
    }

    // ── Painel central ────────────────────────────────────────────────────────
    private JPanel criarPainelCentro() {
        JPanel painel = new JPanel(new BorderLayout(0, 16));
        painel.add(criarPainelFormulario(), BorderLayout.NORTH);
        painel.add(criarPainelTabela(),     BorderLayout.CENTER);
        return painel;
    }

    // ── Formulário da pizza ───────────────────────────────────────────────────
    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Configuração da Pizza"));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.fill   = GridBagConstraints.HORIZONTAL;

        // Linha 0: Formato + Dimensão
        g.gridy = 0; g.weightx = 0;
        g.gridx = 0; painel.add(new JLabel("Formato:"), g);
        g.gridx = 1; g.weightx = 0.5;
        cbFormato = new JComboBox<>(new String[]{"Círculo", "Quadrado", "Triângulo"});
        painel.add(cbFormato, g);

        g.gridx = 2; g.weightx = 0;
        painel.add(new JLabel("Dimensão (cm):"), g);
        g.gridx = 3; g.weightx = 0.5;
        txtTamanho = new JTextField();
        painel.add(txtTamanho, g);

        // Linha 1: Sabor 1
        g.gridy = 1; g.weightx = 0;
        g.gridx = 0; painel.add(new JLabel("Sabor 1 - Categoria:"), g);
        g.gridx = 1; g.weightx = 0.5;
        cbTipo1  = new JComboBox<>();
        cbSabor1 = new JComboBox<>();
        painel.add(cbTipo1, g);
        g.gridx = 2; g.weightx = 0; painel.add(new JLabel("Sabor 1:"), g);
        g.gridx = 3; g.weightx = 0.5; painel.add(cbSabor1, g);

        // Linha 2: Sabor 2 (opcional)
        g.gridy = 2; g.weightx = 0;
        g.gridx = 0;
        chkSabor2 = new JCheckBox("Sabor 2 - Categoria:");
        chkSabor2.addActionListener(e -> atualizarEstadoSabor2());
        painel.add(chkSabor2, g);
        g.gridx = 1; g.weightx = 0.5;
        cbTipo2  = new JComboBox<>();
        cbSabor2 = new JComboBox<>();
        painel.add(cbTipo2, g);
        g.gridx = 2; g.weightx = 0; painel.add(new JLabel("Sabor 2:"), g);
        g.gridx = 3; g.weightx = 0.5; painel.add(cbSabor2, g);

        return painel;
    }

    // ── Tabela de pizzas ──────────────────────────────────────────────────────
    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout(0, 8));
        painel.setBorder(BorderFactory.createTitledBorder("Pizzas do Pedido"));

        String[] colunas = {"Formato", "Dimensão (cm)", "Sabor 1", "Sabor 2", "Valor (R$)"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };

        tabelaPizzas = new JTable(modeloTabela);
        tabelaPizzas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaPizzas.getTableHeader().setReorderingAllowed(false);

        painel.add(new JScrollPane(tabelaPizzas), BorderLayout.CENTER);
        return painel;
    }

    // ── Botões ────────────────────────────────────────────────────────────────
    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));

        btnLimpar          = new JButton("Limpar Campos");
        btnRemoverPizza    = new JButton("Remover Pizza");
        btnAdicionarPizza  = new JButton("Adicionar Pizza");
        btnConfirmarPedido = new JButton("Confirmar Pedido");

        btnLimpar.addActionListener(e -> limpar());
        btnRemoverPizza.addActionListener(e -> removerPizza());
        btnAdicionarPizza.addActionListener(e -> adicionarPizza());
        btnConfirmarPedido.addActionListener(e -> confirmarPedido());

        painel.add(btnLimpar);
        painel.add(btnRemoverPizza);
        painel.add(btnAdicionarPizza);
        painel.add(btnConfirmarPedido);

        return painel;
    }

    // ── Lógica ────────────────────────────────────────────────────────────────

    /**
     * Recarrega os combos de tipo e sabor com os dados atuais do GerenciadorSabores.
     * Deve ser chamado sempre que a TelaPizza salvar/alterar sabores.
     */
    public void recarregarSabores() {
        popularTipos(cbTipo1, cbSabor1);
        popularTipos(cbTipo2, cbSabor2);
    }

    private void popularTipos(JComboBox<TipoSabor> cbTipo, JComboBox<Sabores> cbSabor) {
        cbTipo.removeAllItems();
        for (TipoSabor t : gerenciadorSabores.listarTipos()) cbTipo.addItem(t);

        cbTipo.setRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(
                    JList<?> list, Object val, int idx, boolean sel, boolean foc) {
                super.getListCellRendererComponent(list, val, idx, sel, foc);
                if (val instanceof TipoSabor t)
                    setText(t.getNome() + String.format(" (R$ %.2f/cm²)", t.getPreco()));
                return this;
            }
        });

        cbTipo.addActionListener(e -> filtrarSabores(cbTipo, cbSabor));
        filtrarSabores(cbTipo, cbSabor);
    }

    private void filtrarSabores(JComboBox<TipoSabor> cbTipo, JComboBox<Sabores> cbSabor) {
        TipoSabor sel = (TipoSabor) cbTipo.getSelectedItem();
        cbSabor.removeAllItems();
        if (sel == null) return;
        for (Sabores s : gerenciadorSabores.listarTodos()) {
            if (s.getTipoSabor() == sel) cbSabor.addItem(s);
        }
        cbSabor.setRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(
                    JList<?> list, Object val, int idx, boolean sel2, boolean foc) {
                super.getListCellRendererComponent(list, val, idx, sel2, foc);
                if (val instanceof Sabores s) setText(s.getNome());
                return this;
            }
        });
    }

    public void exibirClienteEncontrado(Cliente cliente) {
        if (cliente == null) {
            lblClienteSelecionado.setText("Cliente não encontrado.");
            lblClienteSelecionado.setForeground(Color.RED);
            gerencia.setCliente(null);
            atualizarEstadoFormulario(false);
        } else {
            lblClienteSelecionado.setText(
                    "Cliente: " + cliente.getNome() + " " + cliente.getSobrenome()
                            + "   |   Telefone: " + cliente.getTelefone()
            );
            lblClienteSelecionado.setForeground(new Color(0, 128, 0));
            lblClienteSelecionado.setFont(lblClienteSelecionado.getFont().deriveFont(Font.BOLD));
            gerencia.setCliente(cliente);
            atualizarEstadoFormulario(true);
        }
    }

    private void adicionarPizza() {
        if (gerencia.getCliente() == null) {
            JOptionPane.showMessageDialog(this,
                    "Busque e selecione um cliente antes de adicionar pizzas.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double dim;
        try {
            dim = Double.parseDouble(txtTamanho.getText().trim().replace(",", "."));
            if (dim <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Informe uma dimensão válida (número positivo).",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Sabores s1 = (Sabores) cbSabor1.getSelectedItem();
        if (s1 == null) {
            JOptionPane.showMessageDialog(this,
                    "Selecione ao menos o Sabor 1.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Sabores> sabores = new ArrayList<>();
        sabores.add(s1);

        String nomeSabor2 = "—";
        if (chkSabor2.isSelected()) {
            Sabores s2 = (Sabores) cbSabor2.getSelectedItem();
            if (s2 != null) {
                sabores.add(s2);
                nomeSabor2 = s2.getNome();
            }
        }

        int formato = cbFormato.getSelectedIndex() + 1;

        try {
            gerencia.fazerAdicionarPizza(dim, formato, sabores);
            double valor = gerencia.getPedido().getValorTotal();

            modeloTabela.addRow(new Object[]{
                    cbFormato.getSelectedItem(),
                    String.format("%.1f", dim),
                    s1.getNome(),
                    nomeSabor2,
                    String.format("R$ %.2f", valor)
            });
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao adicionar pizza: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerPizza() {
        int linha = tabelaPizzas.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecione uma pizza na tabela para remover.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        modeloTabela.removeRow(linha);
    }

    private void confirmarPedido() {
        if (gerencia.getCliente() == null) {
            JOptionPane.showMessageDialog(this,
                    "Nenhum cliente vinculado ao pedido.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (modeloTabela.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Adicione ao menos uma pizza antes de confirmar.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }


        gerencia.confirmarESalvarPedido();

        JOptionPane.showMessageDialog(this,
                "Pedido confirmado com sucesso!",
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        limpar();
    }

    private void limpar() {
        txtBuscaCliente.setText("");
        lblClienteSelecionado.setText("Nenhum cliente selecionado.");
        lblClienteSelecionado.setForeground(Color.GRAY);
        lblClienteSelecionado.setFont(lblClienteSelecionado.getFont().deriveFont(Font.ITALIC));
        gerencia.setCliente(null);
        txtTamanho.setText("");
        cbFormato.setSelectedIndex(0);
        chkSabor2.setSelected(false);
        recarregarSabores();
        atualizarEstadoSabor2();
        atualizarEstadoFormulario(false);
        modeloTabela.setRowCount(0);
    }

    private void atualizarEstadoSabor2() {
        boolean ativo = chkSabor2.isSelected();
        cbTipo2.setEnabled(ativo);
        cbSabor2.setEnabled(ativo);
    }

    /**
     * Atualiza os componentes visuais da tela com os dados do pedido
     * que está atualmente no GerenciaPedidoPizza.
     */
    public void carregarDadosDoPedidoAtual() {
        Pedido p = gerencia.getPedido();

        // 1. Carrega o cliente
        if (p.getCliente() != null) {
            exibirClienteEncontrado(p.getCliente());
            // Preenche o campo de busca com o telefone para facilitar
            txtBuscaCliente.setText(p.getCliente().getTelefone());
        } else {
            exibirClienteEncontrado(null);
            txtBuscaCliente.setText("");
        }

        // 2. Recarrega a tabela de pizzas
        modeloTabela.setRowCount(0);

        if (p.getListaPizza() != null) {
            for (Pizza pizza : p.getListaPizza()) {

                // Formato (Circulo, Quadrado, etc) pelo nome da classe
                String formato = pizza.getClass().getSimpleName();
                String dimensao = String.valueOf(pizza.getDimensao());

                String s1 = pizza.getsabor1();
                String s2 = "";
                if(pizza.getSabores().size() > 1) { s2 = pizza.getsabor2(); }

                modeloTabela.addRow(new Object[]{
                        formato,
                        dimensao,
                        s1,
                        s2,
                        String.format("R$ %.2f", pizza.calculaPrecoInteiro())
                });
            }
        }
    }
    private void atualizarEstadoFormulario(boolean habilitado) {
        cbFormato.setEnabled(habilitado);
        txtTamanho.setEnabled(habilitado);
        cbTipo1.setEnabled(habilitado);
        cbSabor1.setEnabled(habilitado);
        chkSabor2.setEnabled(habilitado);
        cbTipo2.setEnabled(habilitado && chkSabor2.isSelected());
        cbSabor2.setEnabled(habilitado && chkSabor2.isSelected());
        btnAdicionarPizza.setEnabled(habilitado);
        btnConfirmarPedido.setEnabled(habilitado);
    }


    // ── Getters para o Controller ─────────────────────────────────────────────
    public JTextField           getTxtBuscaCliente()     { return txtBuscaCliente; }
    public JButton              getBtnBuscarCliente()    { return btnBuscarCliente; }
    public JComboBox<TipoSabor> getCbTipo1()             { return cbTipo1; }
    public JComboBox<Sabores>   getCbSabor1()            { return cbSabor1; }
    public JCheckBox            getChkSabor2()           { return chkSabor2; }
    public JComboBox<TipoSabor> getCbTipo2()             { return cbTipo2; }
    public JComboBox<Sabores>   getCbSabor2()            { return cbSabor2; }
    public JComboBox<String>    getCbFormato()           { return cbFormato; }
    public JTextField           getTxtTamanho()          { return txtTamanho; }
    public JTable               getTabelaPizzas()        { return tabelaPizzas; }
    public DefaultTableModel    getModeloTabela()        { return modeloTabela; }
    public JButton              getBtnAdicionarPizza()   { return btnAdicionarPizza; }
    public JButton              getBtnRemoverPizza()     { return btnRemoverPizza; }
    public JButton              getBtnConfirmarPedido()  { return btnConfirmarPedido; }
    public JButton              getBtnLimpar()           { return btnLimpar; }
    public GerenciaPedidoPizza  getGerencia()            { return gerencia; }
}