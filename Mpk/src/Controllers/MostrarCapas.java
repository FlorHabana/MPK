package Controllers;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.esri.client.local.ArcGISLocalDynamicMapServiceLayer;
import com.esri.toolkit.legend.JLegend;
import com.esri.map.JMap;
import com.esri.map.LayerInfo;

public class MostrarCapas {
	
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
	
	public void llenarLista(@SuppressWarnings("rawtypes") JComboBox CmbCapas, JMap map, JTextArea txtLista){
		CmbCapas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < map.getLayers().size(); i++) {
					ArcGISLocalDynamicMapServiceLayer layer = (ArcGISLocalDynamicMapServiceLayer) map.getLayers().get(i);
					for (int j = 0; j < layer.getLayersList().size(); j++) {
						if(CmbCapas.getSelectedItem().equals(layer.getSubLayer(j).getName())){
							System.out.println("layer.getSubLayer(i) name : " +layer.getSubLayer(j).getName());
							Object obj = layer.getSubLayer(j);
							System.out.println("objeto : " +obj);
							
						}
					}
				}
			}
		});
	}
}
