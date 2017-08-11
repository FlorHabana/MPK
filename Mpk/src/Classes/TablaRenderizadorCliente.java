package Classes;


import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Rolando
 */
public class TablaRenderizadorCliente implements TableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel etiqueta = new JLabel();
        etiqueta.setOpaque(true);
        /*
         * Coloreamos las filas
         */
        if (row % 2 == 0) {
            etiqueta.setBackground(new Color(173, 216, 230));
        } else {
            etiqueta.setBackground(Color.white);
        }
        /*
         * Para establecer el tipo de icono
         */
        if (column == 1) {
            String nombre = (String) value;
            etiqueta.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            etiqueta.setText(value.toString());
        } else {
            etiqueta.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            etiqueta.setText(value.toString());
        }
        /*
         * Para una fila seleccionada
         */
        if (isSelected) {
            etiqueta.setBackground(new Color(151, 193, 215));
        }
        return etiqueta;
    }
}

