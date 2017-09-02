package Classes;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;

public class Test extends JPanel {

    protected Object[][] data = {
        {"Mary", "Campione", "Snowboarding", new Integer(5), new Boolean(false)},
        {"Alison", "Huml", "Rowing", new Integer(3), new Boolean(true)},
        {"Kathy", "Walrath", "Chasing toddlers", new Integer(2), new Boolean(false)},
        {"Mark", "Andrews", "Speed reading", new Integer(20), new Boolean(true)},
        {"Angela", "Lih", "Teaching high school", new Integer(4), new Boolean(false)}
    };

    protected String[] columnNames = {"First Name", "Last Name", "Sport", "# of Years", "Vegetarian"};

    public Test() {
        JTable table = new JTable(data, columnNames);
        table.setRowHeight(50);
        TableColumn yearsColumn = table.getColumnModel().getColumn(3);

        JSliderEditor editor = new JSliderEditor();
        yearsColumn.setCellEditor(editor);

        setLayout(new GridLayout(1,1));
        add(table);
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new Test());
        frame.setBounds(100,100,800,200);
        frame.setVisible(true);
        return;
    }
}