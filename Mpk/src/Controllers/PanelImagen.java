package Controllers;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelImagen extends JPanel {

	public void paintComponent(Graphics g) {
		Dimension tam=getSize();
		System.out.println("gsdhsagdjagsj");
		ImageIcon imagen = new ImageIcon(VentanaPrincipal.class.getResource("/img/fondoAzul.jpg"));
		g.drawImage(imagen.getImage(), 0, 0, tam.width,tam.height,null);
	}
}