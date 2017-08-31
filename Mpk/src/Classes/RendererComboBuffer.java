package Classes;

import java.awt.Color;
import java.awt.Component;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

@SuppressWarnings({ "serial", "rawtypes" })
public class RendererComboBuffer extends JLabel implements ListCellRenderer {
private ImageIcon[] items;
    
	public RendererComboBuffer(ImageIcon[] items){
        setOpaque(true);
        this.items = items;            
    }
	
	public Component getListCellRendererComponent(JList list, Object value, int index,boolean isSelected, boolean cellHasFocus){
		int selectedIndex = ((Integer)value).intValue();

		if (isSelected){
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(Color.WHITE);
			setForeground(list.getSelectionForeground());
		}
		
		ImageIcon icon = this.items[selectedIndex];
		setIcon( icon );
		File f = new File(items[selectedIndex].toString());
		String name_aux=f.getName();
		String[] name = name_aux.split("\\.");
		if(name[0].equals("buffer1")){
			setText("");
		}else if(name[0].equals("EditingLineTool16")){
			setText("Línea");
		}else if(name[0].equals("EditingPointTool16")){
			setText("Punto");
		}else if(name[0].equals("EditingPolygonTool16")){
			setText("Polígono");
		}
		return this;
	}
}