package controller;

import DAO.GerenciadorCliente;
import model.Cliente;
import view.TelaCliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.List;

public class ClienteController {

    private final TelaCliente view;
    private final GerenciadorCliente gerenciador;

    public ClienteController(TelaCliente view, GerenciadorCliente gerenciador) {
        this.view = view;
        this.gerenciador = gerenciador;

        inicializarEventos();
        carregarTabelaCompleta();
    }

    private void inicializarEventos() {
        view.getBtnSalvar().addActionListener((ActionEvent e) -> acaoSalvar());
        view.getBtnLimpar().addActionListener((ActionEvent e) -> limparCampos());
        view.getBtnExcluir().addActionListener((ActionEvent e) -> acaoExcluir());
        view.getBtnAtualizar().addActionListener((ActionEvent e) -> acaoAtualizar());

        view.getTabelaClientes().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.getTabelaClientes().getSelectedRow() != -1) {
                preencherFormularioComLinhaSelecionada();
            }
        });

        view.getTxtPesquisa().getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { executarFiltro(); }
            @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { executarFiltro(); }
            @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { executarFiltro(); }

            private void executarFiltro() {
                try {
                    String termo = view.getTxtPesquisa().getText();
                    atualizarTabela(gerenciador.filtrarClientes(termo));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Erro ao filtrar clientes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        silenciarCamposVazios(
                view.getTxtNome(),
                view.getTxtSobrenome(),
                view.getTxtTelefone(),
                view.getTxtPesquisa()
        );
    }

    private void carregarTabelaCompleta() {
        try {
            atualizarTabela(gerenciador.listarTodos());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erro ao carregar clientes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acaoSalvar() {
        String nome = view.getTxtNome().getText().trim();
        String sobrenome = view.getTxtSobrenome().getText().trim();
        String telefone = view.getTxtTelefone().getText().trim();

        if (nome.isEmpty() || sobrenome.isEmpty() || telefone.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Todos os campos são obrigatórios.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Cliente novoCliente = new Cliente(null, nome, sobrenome, telefone);
            gerenciador.adicionarCliente(novoCliente);

            JOptionPane.showMessageDialog(view, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acaoExcluir() {
        int linhaSelecionada = view.getTabelaClientes().getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(view, "Selecione um cliente na tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String telefoneParaExcluir = (String) view.getModeloTabela().getValueAt(linhaSelecionada, 2);

        int confirmacao = JOptionPane.showConfirmDialog(view,
                "Tem certeza que deseja excluir este cliente e seus pedidos?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                boolean removido = gerenciador.removerCliente(telefoneParaExcluir);
                if (removido) {
                    JOptionPane.showMessageDialog(view, "Cliente removido com sucesso.");
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(view, "Cliente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erro ao remover cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void acaoAtualizar() {
        int linhaSelecionada = view.getTabelaClientes().getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(view, "Selecione um cliente na tabela para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String telefoneAntigo = (String) view.getModeloTabela().getValueAt(linhaSelecionada, 2);
        String nomeNovo = view.getTxtNome().getText().trim();
        String sobrenomeNovo = view.getTxtSobrenome().getText().trim();
        String telefoneNovo = view.getTxtTelefone().getText().trim();

        if (nomeNovo.isEmpty() || sobrenomeNovo.isEmpty() || telefoneNovo.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nenhum campo pode ficar vazio.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Cliente dadosNovos = new Cliente(null, nomeNovo, sobrenomeNovo, telefoneNovo);
            gerenciador.atualizarCliente(telefoneAntigo, dadosNovos);

            JOptionPane.showMessageDialog(view, "Dados atualizados com sucesso!");
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Erro na Atualização", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherFormularioComLinhaSelecionada() {
        int linha = view.getTabelaClientes().getSelectedRow();
        view.getTxtNome().setText((String) view.getModeloTabela().getValueAt(linha, 0));
        view.getTxtSobrenome().setText((String) view.getModeloTabela().getValueAt(linha, 1));
        view.getTxtTelefone().setText((String) view.getModeloTabela().getValueAt(linha, 2));
    }

    private void limparCampos() {
        view.getTxtNome().setText("");
        view.getTxtSobrenome().setText("");
        view.getTxtTelefone().setText("");
        view.getTxtPesquisa().setText("");
        view.getTabelaClientes().clearSelection();
        carregarTabelaCompleta();
    }

    private void atualizarTabela(List<Cliente> lista) {
        DefaultTableModel modelo = view.getModeloTabela();
        modelo.setRowCount(0);

        for (Cliente c : lista) {
            modelo.addRow(new Object[]{ c.getNome(), c.getSobrenome(), c.getTelefone() });
        }
    }

    private void silenciarCamposVazios(JTextField... campos) {
        for (JTextField campo : campos) {
            campo.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyPressed(java.awt.event.KeyEvent e) {
                    if (e.getKeyCode() == java.awt.event.KeyEvent.VK_BACK_SPACE && campo.getText().isEmpty()) {
                        e.consume();
                    }
                }
            });
        }
    }
}