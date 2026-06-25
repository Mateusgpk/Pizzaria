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
 *
 * @version 1.2
 */
public class TelaPedido extends JPanel {

    // ── Painel de busca de cliente ────────────────────────────────────────────
    private JTextField txtBuscaCliente;
    private JButton btnBuscarCliente;
    private JLabel lblClienteSelecionado;

    // ── Formulário da pizza ───────────────────────────────────────────────────
    private JComboBox<String> cbModoEntrada; // NOVO: Define se é Lado/Raio ou Área
    private JComboBox<TipoSabor> cbTipo1;
    private JComboBox<Sabores> cbSabor1;
    private JCheckBox chkSabor2;
    private JComboBox<TipoSabor> cbTipo2;
    private JComboBox<Sabores> cbSabor2;
    private JComboBox<String> cbFormato;
    private JTextField txtTamanho;

    // ── Tabela de pizzas adicionadas ──────────────────────────────────────────
    private JTable tabelaPizzas;
    private DefaultTableModel modeloTabela;

    // ── Botões ────────────────────────────────────────────────────────────────
    private JButton btnAdicionarPizza;
    private JButton btnRemoverPizza;
    private JButton btnConfirmarPedido;
    private JButton btnLimpar;

    // ── Dependências ──────────────────────────────────────────────────────────
    private final GerenciaPedidoPizza gerencia;
    private final GerenciadorSabores gerenciadorSabores;

    public TelaPedido(GerenciadorSabores gerenciadorSabores, GerenciaPedidoPizza gerenciaPedidoPizza) {
        this.gerenciadorSabores = gerenciadorSabores;
        this.gerencia = gerenciaPedidoPizza;

        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(25, 25, 25, 25));

        add(criarPainelCliente(), BorderLayout.NORTH);
        add(criarPainelCentro(), BorderLayout.CENTER);
        add(criarPainelBotoes(), BorderLayout.SOUTH);

        recarregarSabores();
        atualizarEstadoSabor2();
        atualizarEstadoFormulario(false);
    }

    private JPanel criarPainelCliente() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Cliente"));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.fill = GridBagConstraints.HORIZONTAL;

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

    private JPanel criarPainelCentro() {
        JPanel painel = new JPanel(new BorderLayout(0, 16));
        painel.add(criarPainelFormulario(), BorderLayout.NORTH);
        painel.add(criarPainelTabela(), BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Configuração da Pizza"));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.fill = GridBagConstraints.HORIZONTAL;

        // Linha 0: Formato + Modo de Entrada + Valor
        g.gridy = 0;
        g.gridx = 0; g.weightx = 0; painel.add(new JLabel("Formato:"), g);

        g.gridx = 1; g.weightx = 0.3;
        cbFormato = new JComboBox<>(new String[]{"Círculo", "Quadrado", "Triângulo"});
        painel.add(cbFormato, g);

        g.gridx = 2; g.weightx = 0; painel.add(new JLabel("Medida em:"), g);

        g.gridx = 3; g.weightx = 0.3;
        // NOVO: Dropdown para o usuário escolher o que vai digitar
        cbModoEntrada = new JComboBox<>(new String[]{"Dimensão (Lado/Raio cm)", "Área (cm²)"});
        painel.add(cbModoEntrada, g);

        g.gridx = 4; g.weightx = 0.4;
        txtTamanho = new JTextField();
        painel.add(txtTamanho, g);

        // Linha 1: Sabor 1
        g.gridy = 1;
        g.gridx = 0; g.weightx = 0; painel.add(new JLabel("Sabor 1 - Categoria:"), g);

        g.gridx = 1; g.gridwidth = 2; g.weightx = 0.5;
        cbTipo1 = new JComboBox<>();
        painel.add(cbTipo1, g);

        g.gridx = 3; g.gridwidth = 1; g.weightx = 0; painel.add(new JLabel("Sabor 1:"), g);

        g.gridx = 4; g.weightx = 0.5;
        cbSabor1 = new JComboBox<>();
        painel.add(cbSabor1, g);

        // Linha 2: Sabor 2
        g.gridy = 2;
        g.gridx = 0; g.weightx = 0;
        chkSabor2 = new JCheckBox("Sabor 2 - Categoria:");
        chkSabor2.addActionListener(e -> atualizarEstadoSabor2());
        painel.add(chkSabor2, g);

        g.gridx = 1; g.gridwidth = 2; g.weightx = 0.5;
        cbTipo2 = new JComboBox<>();
        painel.add(cbTipo2, g);

        g.gridx = 3; g.gridwidth = 1; g.weightx = 0; painel.add(new JLabel("Sabor 2:"), g);

        g.gridx = 4; g.weightx = 0.5;
        cbSabor2 = new JComboBox<>();
        painel.add(cbSabor2, g);

        return painel;
    }

    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout(0, 8));
        painel.setBorder(BorderFactory.createTitledBorder("Pizzas do Pedido"));

        // NOVO: Coluna de Área adicionada
        String[] colunas = {"Formato", "Dimensão (cm)", "Área (cm²)", "Sabor 1", "Sabor 2", "Valor (R$)"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        tabelaPizzas = new JTable(modeloTabela);
        tabelaPizzas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaPizzas.getTableHeader().setReorderingAllowed(false);

        painel.add(new JScrollPane(tabelaPizzas), BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));

        btnLimpar = new JButton("Limpar Campos");
        btnRemoverPizza = new JButton("Remover Pizza");
        btnAdicionarPizza = new JButton("Adicionar Pizza");
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

    public void recarregarSabores() {
        popularTipos(cbTipo1, cbSabor1);
        popularTipos(cbTipo2, cbSabor2);
    }

    private void popularTipos(JComboBox<TipoSabor> cbTipo, JComboBox<Sabores> cbSabor) {
        cbTipo.removeAllItems();
        for (TipoSabor t : gerenciadorSabores.listarTipos()) cbTipo.addItem(t);

        cbTipo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object val, int idx, boolean sel, boolean foc) {
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
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object val, int idx, boolean sel2, boolean foc) {
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
            JOptionPane.showMessageDialog(this, "Busque e selecione um cliente antes de adicionar pizzas.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double valorInput;
        try {
            valorInput = Double.parseDouble(txtTamanho.getText().trim().replace(",", "."));
            if (valorInput <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Informe um valor numérico positivo válido.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Sabores s1 = (Sabores) cbSabor1.getSelectedItem();
        if (s1 == null) {
            JOptionPane.showMessageDialog(this, "Selecione ao menos o Sabor 1.", "Atenção", JOptionPane.WARNING_MESSAGE);
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
        double dimensaoFinal = valorInput; // Por padrão, assume que o usuário digitou a dimensão

        try {
            // NOVO: Verifica se o usuário escolheu digitar a Área em vez da Dimensão
            if (cbModoEntrada.getSelectedIndex() == 1) {
                dimensaoFinal = gerencia.calcularDimensaoPorArea(formato, valorInput);

                // Exibe para o usuário qual foi a dimensão calculada pelo sistema
                JOptionPane.showMessageDialog(this,
                        String.format("Área solicitada: %.2f cm²\nDimensão (Lado/Raio) calculada: %.2f cm", valorInput, dimensaoFinal),
                        "Cálculo de Área", JOptionPane.INFORMATION_MESSAGE);
            }

            // Tenta adicionar a pizza usando a dimensão (seja a digitada ou a recém-calculada)
            gerencia.fazerAdicionarPizza(dimensaoFinal, formato, sabores);

            // Se passou daqui, a pizza foi criada com sucesso (passou nas validações).
            // Pega a última pizza adicionada para extrair a área exata calculada na Entidade
            Pizza pizzaCriada = gerencia.getPedido().getListaPizza().get(gerencia.getPedido().getListaPizza().size() - 1);

            modeloTabela.addRow(new Object[]{
                    cbFormato.getSelectedItem(),
                    String.format("%.2f", dimensaoFinal),
                    String.format("%.2f", pizzaCriada.getCms()), // Área real armazenada
                    s1.getNome(),
                    nomeSabor2,
                    String.format("R$ %.2f", pizzaCriada.calculaPrecoInteiro())
            });

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerPizza() {
        int linha = tabelaPizzas.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma pizza na tabela para remover.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        gerencia.removerPizzaPorIndice(linha);
        modeloTabela.removeRow(linha);
    }

    private void confirmarPedido() {
        if (gerencia.getCliente() == null) {
            JOptionPane.showMessageDialog(this, "Nenhum cliente vinculado ao pedido.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (modeloTabela.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Adicione ao menos uma pizza antes de confirmar.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        gerencia.confirmarESalvarPedido();
        JOptionPane.showMessageDialog(this, "Pedido confirmado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        limpar();
    }

    private void limpar() {
        txtBuscaCliente.setText("");
        lblClienteSelecionado.setText("Nenhum cliente selecionado.");
        lblClienteSelecionado.setForeground(Color.GRAY);
        lblClienteSelecionado.setFont(lblClienteSelecionado.getFont().deriveFont(Font.ITALIC));
        gerencia.setCliente(null);
        txtTamanho.setText("");
        cbModoEntrada.setSelectedIndex(0);
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

    public void carregarDadosDoPedidoAtual() {
        Pedido p = gerencia.getPedido();

        if (p.getCliente() != null) {
            exibirClienteEncontrado(p.getCliente());
            txtBuscaCliente.setText(p.getCliente().getTelefone());
        } else {
            exibirClienteEncontrado(null);
            txtBuscaCliente.setText("");
        }

        modeloTabela.setRowCount(0);

        if (p.getListaPizza() != null) {
            for (Pizza pizza : p.getListaPizza()) {
                String formato = pizza.getClass().getSimpleName();

                // NOVO: Como Pizza implementa Forma, podemos pegar a dimensão nativa dela
                double dimensao = ((Forma) pizza).getDimensao();

                String s1 = pizza.getsabor1();
                String s2 = "";
                if (pizza.getSabores().size() > 1) {
                    s2 = pizza.getsabor2();
                }

                modeloTabela.addRow(new Object[]{
                        formato,
                        String.format("%.2f", dimensao),
                        String.format("%.2f", pizza.getCms()), // Área
                        s1,
                        s2,
                        String.format("R$ %.2f", pizza.calculaPrecoInteiro())
                });
            }
        }
    }

    private void atualizarEstadoFormulario(boolean habilitado) {
        cbFormato.setEnabled(habilitado);
        cbModoEntrada.setEnabled(habilitado);
        txtTamanho.setEnabled(habilitado);
        cbTipo1.setEnabled(habilitado);
        cbSabor1.setEnabled(habilitado);
        chkSabor2.setEnabled(habilitado);
        cbTipo2.setEnabled(habilitado && chkSabor2.isSelected());
        cbSabor2.setEnabled(habilitado && chkSabor2.isSelected());
        btnAdicionarPizza.setEnabled(habilitado);
        btnConfirmarPedido.setEnabled(habilitado);
    }

    // Getters
    public JTextField getTxtBuscaCliente() { return txtBuscaCliente; }
    public JButton getBtnBuscarCliente() { return btnBuscarCliente; }
    public JComboBox<String> getCbModoEntrada() { return cbModoEntrada; }
}