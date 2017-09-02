package Classes;

import java.awt.Component;
import java.awt.Font;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.tree.TreeCellEditor;

public class JSliderEditorP extends AbstractCellEditor implements TreeCellEditor{
	private static final long serialVersionUID = 1L;
	protected JSlider slider=null;
	private Hashtable<Integer, JLabel> opacitylabelTable = new Hashtable<Integer, JLabel>();
	
	public JSliderEditorP(){
		slider = new JSlider();
		this.slider.setVisible(true);
		valuesToSlider(opacitylabelTable);
		this.slider.setLabelTable(opacitylabelTable);
		this.slider.setMajorTickSpacing(50);
		this.slider.setPaintTicks(true);
		this.slider.setPaintLabels(true);
		this.slider.setFont(new Font("Serif", Font.ITALIC, 8));
	}
	
	public Object getCellEditorValue() {
		return new Integer(slider.getValue());
	}

	public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
			boolean leaf, int row) {
		 Integer val = (Integer)value;
	     slider.setValue(val.intValue());
		return slider;
	}
	
	private void valuesToSlider(Hashtable<Integer, JLabel> table){
		table.put(new Integer(100), new JLabel("1"));   
		table.put(new Integer(50), new JLabel("0.5"));   
		table.put(new Integer(0), new JLabel("0"));  
	}

}
