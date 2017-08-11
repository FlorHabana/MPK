package Classes;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Controllers.MainApplication;
import Controllers.VentanaPrincipal;

public class FondoModal extends JPanel{
	public void paintComponent(Graphics g) {
		Dimension tam=getSize();
		ImageIcon imagen = new ImageIcon(MainApplication.class.getResource("/img/fondoModal.png"));
		g.drawImage(imagen.getImage(), 0, 0, tam.width,tam.height,null);
	}
}
