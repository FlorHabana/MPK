package Classes;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Render extends JFrame {
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				Render render = new Render();
				render.setVisible(true);
			}
		});
	}
	
	public Render(){
		this.setBounds(100, 100, 400, 100);
		this.setContentPane(new View());
	}
}
