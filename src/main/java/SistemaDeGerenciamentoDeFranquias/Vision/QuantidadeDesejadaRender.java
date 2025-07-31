package SistemaDeGerenciamentoDeFranquias.Vision;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

class QuantidadeDesejadaRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        Boolean selecionado = (Boolean) table.getValueAt(row, 0);
        if (selecionado != null && selecionado) {
            c.setBackground(Color.YELLOW);
        } else {
            c.setBackground(Color.LIGHT_GRAY);
        }
        setHorizontalAlignment(SwingConstants.CENTER);
        return c;
    }
}
