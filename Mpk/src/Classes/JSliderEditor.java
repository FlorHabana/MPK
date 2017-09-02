package Classes;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.EventObject;
import java.util.Hashtable;


public class JSliderEditor extends AbstractCellEditor implements TableCellEditor {

	private static final long serialVersionUID = 1L;
	protected JSlider slider = null;
    private Hashtable<Integer, JLabel> opacitylabelTable = new Hashtable<Integer, JLabel>();

    public JSliderEditor() {
    	slider= new JSlider();
    	this.slider.setVisible(true);
		valuesToSlider(opacitylabelTable);
		this.slider.setLabelTable(opacitylabelTable);
		this.slider.setMajorTickSpacing(50);
		this.slider.setPaintTicks(true);
		this.slider.setPaintLabels(true);
		this.slider.setFont(new Font("Serif", Font.ITALIC, 8));
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        Integer val = (Integer)value;
        slider.setValue(val.intValue());
        return slider;
    }

    public Object getCellEditorValue() {
        return new Integer(slider.getValue());
    }
    
    private void valuesToSlider(Hashtable<Integer, JLabel> table){
		table.put(new Integer(100), new JLabel("1"));   
		table.put(new Integer(50), new JLabel("0.5"));   
		table.put(new Integer(0), new JLabel("0"));  
	}
}