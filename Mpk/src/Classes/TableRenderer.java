package Classes;

import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.tree.TreeCellEditor;

import java.awt.*;

public class TableRenderer extends JPanel {
	private static final long serialVersionUID =1L;
	private JCheckBox box = new JCheckBox();
	private JLabel label = new JLabel();
	protected Object[][] data = {
			{/*box,label,*/""}
	};
	protected String[] names={/*"","",*/""};
	
	public TableRenderer(){
		JTable table = new JTable(data,names);
		table.setRowHeight(50);
		TableColumn opacity = table.getColumnModel().getColumn(0);
		JSliderEditor editor = new JSliderEditor();
		opacity.setCellEditor(editor);
		
		JTree tree = new JTree();
		JSliderEditorP editor1 = new JSliderEditorP();
		tree.setCellEditor(editor1);
		
		setLayout(new BoxLayout(this, 0));
		
		add(table);
		add(tree);
	}
	
	 public static void main(String args[]) {
	        JFrame frame = new JFrame();
	        frame.getContentPane().add(new TableRenderer());
	        frame.setBounds(100,100,800,200);
	        frame.setVisible(true);
	        return;
	    }
}
