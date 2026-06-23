package controller;

import DAO.GerenciadorSabores;
import model.Sabores;
import model.TipoSabor;
import view.TelaPizza;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Controlador da tela de Sabores e Preços.
 */
public class PizzaController {

    private final TelaPizza view;
    private final GerenciadorSabores gerenciador;

    public PizzaController(TelaPizza view, GerenciadorSabores gerenciador) {
        this.view = view;
        this.gerenciador = gerenciador;
        inicializarEventos();
        atualizarTabelaSabores(gerenciador.listarTodos());
    }

    private void inicializarEventos() {
        view.getBtnSalvarSabor().addActionListener(e -> acaoSalvarSabor());
        view.getBtnAtualizarSabor().addActionListener(e -> acaoAtualizarSabor());
        view.getBtnExcluirSabor().addActionListener(e -> acaoExcluirSabor());
        view.getBtnLimparSabor().addActionListener(e -> limparCampos());
        view.getBtnSalvarPrecos().addActionListener(e -> acaoSalvarPrecos());

        view.getTabelaSabores().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.getTabelaSabores().getSelectedRow() != -1) {
                preencherFormularioComLinhaSelecionada();
            }
        });

        silenciarCamposVazios(view.getTxtNomeSabor());
    }

    private void acaoSalvarSabor() {
        String nome = view.getTxtNomeSabor().getText().trim();
        TipoSabor tipo = (TipoSabor) view.getCmbTipoSabor().getSelectedItem();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Digite o nome do sabor.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            gerenciador.adicionarSabor(new Sabores(tipo, nome));
            JOptionPane.showMessageDialog(view, "Sabor cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            atualizarTabelaSabores(gerenciador.listarTodos());
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acaoAtualizarSabor() {
        int linha = view.getTabelaSabores().getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(view, "Selecione um sabor na tabela para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String nome = view.getTxtNomeSabor().getText().trim();
        TipoSabor tipo = (TipoSabor) view.getCmbTipoSabor().getSelectedItem();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nenhum campo pode ficar vazio.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            gerenciador.atualizarSabor(linha, nome, tipo);
            JOptionPane.showMessageDialog(view, "Sabor atualizado com sucesso!");
            limparCampos();
            atualizarTabelaSabores(gerenciador.listarTodos());
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acaoExcluirSabor() {
        int linha = view.getTabelaSabores().getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(view, "Selecione um sabor na tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmacao = JOptionPane.showConfirmDialog(view,
                "Deseja excluir este sabor?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            gerenciador.removerSabor(linha);
            limparCampos();
            atualizarTabelaSabores(gerenciador.listarTodos());
        }
    }

    private void acaoSalvarPrecos() {
        List<TipoSabor> tipos = gerenciador.listarTipos();
        DefaultTableModel modelo = view.getModeloTabelaPrecos();
        try {
            for (int i = 0; i < tipos.size(); i++) {
                String valor = modelo.getValueAt(i, 1).toString().replace(",", ".");
                double preco = Double.parseDouble(valor);
                if (preco <= 0) throw new IllegalArgumentException("Preço deve ser positivo.");
                tipos.get(i).setPreco(preco);
            }
            JOptionPane.showMessageDialog(view, "Preços atualizados com sucesso!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Valor inválido. Use apenas números (ex: 0.25).", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherFormularioComLinhaSelecionada() {
        int linha = view.getTabelaSabores().getSelectedRow();
        Sabores s = gerenciador.listarTodos().get(linha);
        view.getTxtNomeSabor().setText(s.getNome());
        view.getCmbTipoSabor().setSelectedItem(s.getTipoSabor());
    }

    private void limparCampos() {
        view.getTxtNomeSabor().setText("");
        view.getCmbTipoSabor().setSelectedIndex(0);
        view.getTabelaSabores().clearSelection();
    }

    private void atualizarTabelaSabores(List<Sabores> lista) {
        DefaultTableModel modelo = view.getModeloTabelaSabores();
        modelo.setRowCount(0);
        for (Sabores s : lista)
            modelo.addRow(new Object[]{s.getNome(), s.getTipoSabor().getNome()});
    }

    private void silenciarCamposVazios(JTextField... campos) {
        for (JTextField campo : campos) {
            campo.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override public void keyPressed(java.awt.event.KeyEvent e) {
                    if (e.getKeyCode() == java.awt.event.KeyEvent.VK_BACK_SPACE && campo.getText().isEmpty())
                        e.consume();
                }
            });
        }
    }
}
