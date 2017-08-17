package Classes;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Controllers.MainApplication;
import Controllers.VentanaPrincipal;

public class FondoModal extends JPanel{
	public FondoModal() {
		
	}
	public void paintComponent(Graphics g) { 
		System.out.println(" paint -----------");
		Dimension tam=getSize();
		ImageIcon imagen = new ImageIcon(MainApplication.class.getResource("/img/menu2.png"));
		System.out.println(" width ----- "+ tam.width + " height "+tam.getHeight()); 
		g.drawImage(imagen.getImage(), 0, 0, tam.width,tam.height,null);
	}
}
