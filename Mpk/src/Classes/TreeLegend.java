package Classes;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

import com.esri.core.map.LayerLegendInfo;
import com.esri.core.map.LegendItemInfo;
import com.esri.map.Layer;
import com.esri.map.LayerInfo;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Hashtable;


@SuppressWarnings("serial")
public class TreeLegend extends JPanel implements TreeCellRenderer{
	private JCheckBox checkBox = new JCheckBox("");
	private JLabel label = new JLabel();
	private JSlider slider = new JSlider();
	private Hashtable<Integer, JLabel> opacitylabelTable = new Hashtable<Integer, JLabel>();
	
	public TreeLegend(){
		add(checkBox);
		add(label);
		add(slider);
	}
	
	private void valuesToSlider(Hashtable<Integer, JLabel> table){
		table.put(new Integer(100), new JLabel("1"));   
		table.put(new Integer(50), new JLabel("0.5"));   
		table.put(new Integer(0), new JLabel("0"));  
	}
	
	public void customizeComponents(Layer layer){
		this.checkBox.setVisible(true);
		this.checkBox.setBackground(new Color(0,0,0,0));
		
		this.label.setBackground(new Color(0,0,0,0));
		this.label.setVisible(true);
		
		this.slider.setBackground(new Color(0,0,0,0));
		this.slider.setOrientation(JSlider.HORIZONTAL);
		this.slider.setMinimum(0);
		this.slider.setMaximum(100);
		valuesToSlider(opacitylabelTable);
		slider.setLabelTable(opacitylabelTable);
		slider.setMajorTickSpacing(50);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setFont(new Font("Serif", Font.ITALIC, 8));
		
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
		this.checkBox.setVisible(true);
		this.checkBox.setBackground(new Color(0,0,0,0));
		
		this.label.setBackground(new Color(0,0,0,0));
		this.label.setVisible(true);
		
		this.slider.setBackground(new Color(0,0,0,0));
		this.slider.setOrientation(JSlider.HORIZONTAL);
		this.slider.setMinimum(0);
		this.slider.setMaximum(100);
		valuesToSlider(opacitylabelTable);
		slider.setLabelTable(opacitylabelTable);
		slider.setMajorTickSpacing(50);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setFont(new Font("Serif", Font.ITALIC, 8));
		
		DefaultMutableTreeNode nodeToRender = (DefaultMutableTreeNode)value;
		final Object userObject = nodeToRender.getUserObject();
		
		if ((userObject instanceof Layer)) {
			final Layer layer = (Layer) userObject;
			renderLayer(layer);
			slider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					System.out.println("Changement;;;;;;;;;;;;;;;;");
					JSlider source=(JSlider) arg0.getSource();
					if(!source.getValueIsAdjusting()){
						layer.setOpacity((float)(source.getValue()/100));
					}
				}
			});
		} else if ((userObject instanceof LayerInfo)) {
			renderSubLayer((LayerInfo)userObject, nodeToRender);
		} else if ((userObject instanceof LayerLegendInfo)) {
			renderLegendInfo((LayerLegendInfo)userObject);
		} else if ((userObject instanceof LegendItemInfo)) {
			renderLegendItem((LegendItemInfo)userObject);
		}
		return this;
	}

	private void renderLegendItem(LegendItemInfo userObject) {
		// TODO Auto-generated method stub
		
	}

	private void renderLegendInfo(LayerLegendInfo userObject) {
		// TODO Auto-generated method stub
		
	}

	private void renderSubLayer(LayerInfo userObject, DefaultMutableTreeNode nodeToRender) {
		// TODO Auto-generated method stub
		
	}

	private void renderLayer(Layer userObject) {
		// TODO Auto-generated method stub
		
	}
}
