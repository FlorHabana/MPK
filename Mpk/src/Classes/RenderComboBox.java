package Classes;

import java.awt.Color;
import java.awt.Component;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

@SuppressWarnings({ "rawtypes", "serial" })
public class RenderComboBox extends JLabel implements ListCellRenderer {
	private ImageIcon[] items;
    
	public RenderComboBox(ImageIcon[] items){
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
		if(name[0].equals("Imagery")){
			setText("Imágenes");
		}else if(name[0].equals("Physical")){
			setText("Físico");
		}else if(name[0].equals("ShadedRelief")){
			setText("Relieve Sombreado");
		}else if(name[0].equals("Street")){
			setText("Calles");
		}else if(name[0].equals("TerrainBase")){
			setText("Terreno");
		}else if(name[0].equals("Topographic")){
			setText("Topográfico");
		}
		return this;
	}
}