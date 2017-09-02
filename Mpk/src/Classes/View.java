package Classes;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSlider;

@SuppressWarnings("serial")
public class View extends JPanel {
	private JCheckBox box;
	private JLabel label;
	private JSlider slider;
	
	public View(){
		box = new JCheckBox();
		add(box);
		
		label = new JLabel();
		add(label);
		
		slider = new JSlider();
		customizeSlider();
		add(slider);
	}
	
	public void customizeSlider(){
		this.slider.setVisible(true);
//		this.slider.setBackground(new Color(0,0,0,0));
//		valuesToSlider(opacitylabelTable);
//		this.slider.setLabelTable(opacitylabelTable);
		this.slider.setMajorTickSpacing(50);
		this.slider.setPaintTicks(true);
		this.slider.setPaintLabels(true);
		this.slider.setFont(new Font("Serif", Font.ITALIC, 8));
//		this.slider.addChangeListener(this.opacityListener(layer));
	}
}