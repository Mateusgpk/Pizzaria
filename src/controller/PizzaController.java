package controller;

import DAO.GerenciadorSabores;
import model.Sabores;
import model.TipoSabor;
import view.TelaPedido;
import view.TelaPizza;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class PizzaController {

    private final TelaPizza view;
    private final TelaPedido viewPedido;
    private final GerenciadorSabores gerenciador;
    private List<Sabores> saboresCarregados = new ArrayList<>();

    public PizzaController(TelaPizza view, GerenciadorSabores gerenciador,TelaPedido telaPedido) {
        this.view = view;
        this.viewPedido=telaPedido;
        this.gerenciador = gerenciador;
        inicializarEventos();
        carregarTabelaSabores();
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

    private void carregarTabelaSabores() {
        try {
            this.saboresCarregados = gerenciador.listarTodos();
            atualizarTabelaSabores(this.saboresCarregados);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erro ao carregar sabores: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acaoSalvarSabor() {
        String nome = view.getTxtNomeSabor().getText().trim();
        TipoSabor tipo = (TipoSabor) view.getCmbTipoSabor().getSelectedItem();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Digite o nome do sabor.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            gerenciador.adicionarSabor(new Sabores(null, tipo, nome));
            JOptionPane.showMessageDialog(view, "Sabor cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            viewPedido.recarregarSabores();
            carregarTabelaSabores();

        } catch (Exception ex) {
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
            Sabores selecionado = saboresCarregados.get(linha);
            selecionado.setNome(nome);
            selecionado.setTipoSabor(tipo);

            gerenciador.atualizarSabor(selecionado);
            JOptionPane.showMessageDialog(view, "Sabor atualizado com sucesso!");
            limparCampos();
            carregarTabelaSabores();
        } catch (Exception ex) {
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
            try {
                Sabores selecionado = saboresCarregados.get(linha);
                gerenciador.removerSabor(selecionado);
                limparCampos();
                carregarTabelaSabores();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erro ao excluir sabor: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void acaoSalvarPrecos() {
        if (view.getTabelaPrecos().isEditing()) {
            view.getTabelaPrecos().getCellEditor().stopCellEditing();
        }
        try {
            List<TipoSabor> tipos = gerenciador.listarTipos();
            DefaultTableModel modelo = view.getModeloTabelaPrecos();

            for (int i = 0; i < tipos.size(); i++) {
                Object celula = modelo.getValueAt(i, 1);
                if (celula == null || celula.toString().trim().isEmpty()) {
                    throw new NumberFormatException();
                }
                String valor = celula.toString().trim().replace(",", ".");
                double preco = Double.parseDouble(valor);
                tipos.get(i).setPreco(preco);
            }

            for (TipoSabor tipo : tipos) {
                gerenciador.atualizarPrecoTipo(tipo);
            }

            viewPedido.recarregarSabores();
            JOptionPane.showMessageDialog(view, "Preços atualizados com sucesso!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Valor inválido. Use apenas números (ex: 0.25).", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherFormularioComLinhaSelecionada() {
        int linha = view.getTabelaSabores().getSelectedRow();
        Sabores s = saboresCarregados.get(linha);
        view.getTxtNomeSabor().setText(s.getNome());
        view.getCmbTipoSabor().setSelectedItem(s.getTipoSabor());
    }

    private void limparCampos() {
        view.getTxtNomeSabor().setText("");
        if (view.getCmbTipoSabor().getItemCount() > 0) {
            view.getCmbTipoSabor().setSelectedIndex(0);
        }
        view.getTabelaSabores().clearSelection();
    }

    private void atualizarTabelaSabores(List<Sabores> lista) {
        DefaultTableModel modelo = view.getModeloTabelaSabores();
        modelo.setRowCount(0);
        for (Sabores s : lista) {
            modelo.addRow(new Object[]{ s.getNome(), s.getTipoSabor().getNome() });
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