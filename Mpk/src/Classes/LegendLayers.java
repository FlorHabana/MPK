package Classes;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.esri.core.map.LayerLegendInfo;
import com.esri.core.map.LegendItemInfo;
import com.esri.map.JMap;
import com.esri.map.Layer;
import com.esri.map.LayerInfo;
import com.esri.map.LayerList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.Font;
import java.util.Hashtable;

/**Clase para agregar la creación de nodos en el JTree 
 * y mostrar la información de las capas del mapa**/


public class LegendLayers extends JPanel{
	private JCheckBox checkBox = new JCheckBox("");
	private JLabel label = new JLabel();
	private JSlider slider = new JSlider();
	private JTree tree = new JTree();
	private JScrollPane scrollPane= new JScrollPane();
	private JMap map;
	
	private Hashtable<Integer, JLabel> opacitylabelTable = new Hashtable<Integer, JLabel>();
	
	public LegendLayers(JMap map){
		this.customizeComponentsBanner();
		this.map = map;
		setLayout(new BoxLayout(this, 0));
		
		this.tree.setBackground(new Color(100, 149, 237));
		scrollPane.setViewportView(this.tree);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
	}
	
	private void rendererMap(JMap map){
		LayerList layerList= map.getLayers();
		for(Layer layer : layerList){
			renderLayer(layer);
			for(int i=0; i<layerList.size();i++){
				
			}
		}
		for(Object object : layerList){
			if(object instanceof Layer){
				renderLayer((Layer)object);
			} else if ((object instanceof LayerInfo)) {
				renderSubLayer((LayerInfo)object);
			} else if ((object instanceof LayerLegendInfo)) {
				renderLegendInfo((LayerLegendInfo)object);
			} else if ((object instanceof LegendItemInfo)) {
				renderLegendInfo((LegendItemInfo)object);
			}
		}
		
	}

	private void renderLegendInfo(LegendItemInfo object) {
		// TODO Auto-generated method stub
		
	}

	private void renderLegendInfo(LayerLegendInfo object) {
		// TODO Auto-generated method stub
		
	}

	private void renderSubLayer(LayerInfo object) {
		// TODO Auto-generated method stub
		
	}

	private void renderLayer(Layer object) {
		// TODO Auto-generated method stub
		
	}

	private void nodeTree(){
		JPanel node= new JPanel();
		node.setLayout(new BoxLayout(node, 0));
		this.customizeComponentsNode();
		node.add(checkBox);
		node.add(label);
		node.add(slider);
	}
	
	private void valuesToSlider(Hashtable<Integer, JLabel> table){
		table.put(new Integer(100), new JLabel("1"));   
		table.put(new Integer(50), new JLabel("0.5"));   
		table.put(new Integer(0), new JLabel("0"));  
	}
	
	private ChangeListener opacityListener(final Layer layer){
		ChangeListener sliderListener= new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source=(JSlider) e.getSource();
				layer.setOpacity((float)(source.getValue()/100));
			}
		};
		return sliderListener;	
	}
	
	private void customizeComponentsNode(){
		this.checkBox.setVisible(true);
		this.checkBox.setBackground(new Color(0,0,0,0));
		
		
		this.label.setBackground(new Color(0,0,0,0));
		this.label.setVisible(true);
		
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

	private void customizeComponentsBanner(){
		
	}
}
