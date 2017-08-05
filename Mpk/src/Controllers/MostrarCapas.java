package Controllers;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.esri.client.local.ArcGISLocalDynamicMapServiceLayer;
import com.esri.toolkit.legend.JLegend;
import com.esri.map.JMap;
import com.esri.map.LayerInfo;

public class MostrarCapas {
	JPanel panel = null;
	
	public String[] llenarCombo(JMap map){
		String nameLayer="";
		for (int i = 0; i < map.getLayers().size(); i++) {
			ArcGISLocalDynamicMapServiceLayer layer = (ArcGISLocalDynamicMapServiceLayer) map.getLayers().get(i);
			for(LayerInfo layer1 : layer.getLayersList()){
				nameLayer+= layer1.getName() +"~";
			}
		}
		String[] names = nameLayer.split("~");
		return names;
	}
	
	public JPanel llenarLista(@SuppressWarnings("rawtypes") final JComboBox CmbCapas, final JMap map){
		
		CmbCapas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < map.getLayers().size(); i++) {
					ArcGISLocalDynamicMapServiceLayer layer = (ArcGISLocalDynamicMapServiceLayer) map.getLayers().get(i);
					for (int j = 0; j < layer.getLayersList().size(); j++) {
						if(CmbCapas.getSelectedItem().equals(layer.getSubLayer(j).getName())){
							DefaultMutableTreeNode padre = new DefaultMutableTreeNode("Principal");
					        DefaultMutableTreeNode capa = new DefaultMutableTreeNode(layer.getSubLayer(j));
					        padre.add(capa);
					        DefaultTreeModel modelo = new DefaultTreeModel(padre);
							
							JTree jTree = new JTree(modelo);
							System.out.println("JTree : " + jTree.getComponentCount());
//							panel.add(jTree, BorderLayout.NORTH);
							panel.add(jTree, BorderLayout.NORTH);
							System.out.println("Panel : " + panel.getComponentCount());
//							System.out.println("jTree : " + jTree.getComponent(0));
							for (int k = 0; k < panel.getComponentCount(); k++) {
								System.out.println("Componentes panel : " +panel.getComponent(k));
							}
							panel = new JPanel(new BorderLayout());
							panel.add(new JScrollPane(jTree));
						}
					}
				}
			}
		});
		return panel;
	}
}