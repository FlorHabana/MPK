package Classes;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComboBox;

@SuppressWarnings({ "serial", "rawtypes" })
public class MyComboBox extends JComboBox {
	@SuppressWarnings("unchecked")
	public MyComboBox(int num_items){   
//		Dimension d = new Dimension(206,26);
//		this.setSize(d);
//		this.setPreferredSize(d);
//		setBackground(new Color(0f, 0f, 0f, 0f));
		//Indices para las imagenes
		for( int i=0; i<num_items; i++){
			this.addItem(i);
		}    
		this.setVisible(true);
	}
}
