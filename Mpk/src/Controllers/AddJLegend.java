package Controllers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;

import javax.swing.CellRendererPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.esri.map.JMap;
import com.esri.map.Layer;
import com.esri.map.LayerList;
import com.esri.toolkit.legend.JLegend;
import com.esri.toolkit.legend.LegendTreeCellRenderer;

import Classes.OwnJLegend;
//import Classes.OwnLegend;

public class AddJLegend{
	Hashtable<Integer, JLabel> opacitylabelTable = new Hashtable<Integer, JLabel>();

	public JSlider sliderOpacity(final JMap map){
		final JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 10);
		slider.setBackground(new Color(0f,0f,0f,0f));
		for(Layer layer : map.getLayers()){
			slider.setEnabled(layer.isVisible());
			slider.setValue((int) (layer.getOpacity()*100));
		}
		valuesToOpacitySlider(opacitylabelTable);
		slider.setLabelTable(opacitylabelTable);
		slider.setMajorTickSpacing(50);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setFont(new Font("Serif", Font.ITALIC, 8));
		slider.addChangeListener(opacity(map));
		return slider;
	}
	
	public void createLegendLayers(final JMap map, JPanel panelMenuCapas){
		OwnJLegend legend=new OwnJLegend(map);
		legend.setPreferredSize(new Dimension(300, 749));
		JScrollPane scrollPane=(JScrollPane) legend.getComponent(0);
		JViewport viewport= (JViewport) scrollPane.getComponent(0);
		viewport.getComponent(0).setBackground(new Color(100, 149, 237));
		panelMenuCapas.add(legend, BorderLayout.WEST);
	}
	
	public void addElements(final JMap map, JPanel panelMenuCapas){
		JLegend legend= new JLegend(map);
		legend.setPreferredSize(new Dimension(300, 749));
		JScrollPane scrollPane=(JScrollPane) legend.getComponent(0);
		JViewport viewport= (JViewport) scrollPane.getComponent(0);
		viewport.getComponent(0).setBackground(new Color(100, 149, 237));
		final JTree jTree= (JTree) viewport.getComponent(0);
		
		DefaultTreeModel defaultTreeModel = (DefaultTreeModel) jTree.getModel();
		System.out.println("feuera del listener defaultTreeModel : "+defaultTreeModel);
//		if (jTree.getPathForLocation(arg0.getX(), arg0.getY()) != null){
//			
//		}
		
		legend.setBorder(new LineBorder(new Color(205, 205, 255), 3));
		jTree.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				DefaultTreeModel defaultTreeModel = (DefaultTreeModel) jTree.getModel();
				System.out.println("defaultTreeModel : "+defaultTreeModel+"~~~~~~~~~~~~~~"+
						jTree.getPathForLocation(e.getX(), e.getY()) );
				if (jTree.getPathForLocation(e.getX(), e.getY()) != null){
					TreePath path = jTree.getPathForLocation(e.getX(), e.getY());
					Object[] objects = path.getPath();
					System.out.println("Path~~~~~~~~"+path);
					for (int i = 0; i < objects.length; i++) {
						System.out.println("objects*************"+objects[i]);
					}
				}
			}
		});
		legend.repaint();
		panelMenuCapas.add(legend, BorderLayout.WEST);
	}
	
	private void valuesToOpacitySlider(Hashtable<Integer, JLabel> table) {
		table.put(new Integer(100), new JLabel("1"));   
		table.put(new Integer(50), new JLabel("0.5"));   
		table.put(new Integer(0), new JLabel("0"));  
	}

	public ChangeListener opacity(final JMap map){
		System.out.println("Se realizó un cambio");
		ChangeListener sliderListener= new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				System.out.println("ssssssss");
				JSlider source = (JSlider) e.getSource();
				System.out.println("___________________"+source.isVisible());
//				if (!source.getValueIsAdjusting()) {
					updateLayers(source, map);
//				}
			}
		};
		return sliderListener;
	}
	
	public void updateLayers(JSlider source, JMap map){
		LayerList layers= map.getLayers(); 
		for (Layer layer : layers) {
			if(layer.isVisible()){
				layer.setOpacity((float)(source.getValue()/100));
			}
		}
	}
}
