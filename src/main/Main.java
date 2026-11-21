package main;

import view.JanelaPrincipal;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());

        } catch (Exception ex) {
            System.err.println("Falha ao inicializar o FlatLaf.");
        }

        SwingUtilities.invokeLater(() -> {
            JanelaPrincipal janela = null;
            try {
                janela = new JanelaPrincipal();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            janela.setVisible(true);
        });
    }
}