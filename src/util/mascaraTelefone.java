package util;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Formatador para aplicar uma máscara visual nos telefones,
 * mantendo o dado padrão (apenas dígitos) armazenado.
 */
public class mascaraTelefone extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {

        if (value != null) {
            String telefone = value.toString().replaceAll("\\D", "");

            if (telefone.length() == 11) {
                value = String.format("(%s) %s-%s",
                        telefone.substring(0, 2),
                        telefone.substring(2, 7),
                        telefone.substring(7));
            }

            else if (telefone.length() == 10) {
                value = String.format("(%s) %s-%s",
                        telefone.substring(0, 2),
                        telefone.substring(2, 6),
                        telefone.substring(6));
            }
        }

        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
