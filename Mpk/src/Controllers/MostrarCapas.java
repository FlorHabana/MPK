package Controllers;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.esri.client.local.ArcGISLocalFeatureLayer;
import com.esri.map.JMap;

public class MostrarCapas {
	
	public String[] llenarCombo(JMap map){
		String nameLayer="";
		for (int i = 0; i < map.getLayers().size(); i++) {
			if(!map.getLayers().get(i).getName().equals("Mapa Base")){
				ArcGISLocalFeatureLayer arcGISLocalFeatureLayer = (ArcGISLocalFeatureLayer) map.getLayers().get(i);
				nameLayer+= arcGISLocalFeatureLayer.getName() +"~";
			}
		}
		String[] names = nameLayer.split("~");
		return names;
	}
	
	public void cleanPanel(JPanel panel_infoCapas){
		for (int i = 0; i < panel_infoCapas.getComponentCount(); i++) {
			panel_infoCapas.remove(i);
		}
	}
	
	public void cleanTabPanel(JTabbedPane panel_infoCapas){
		try {
			if (panel_infoCapas.getComponentCount() != 0) {
				panel_infoCapas.removeAll(); 
//				if (panel_infoCapas.getComponentCount() != 0) {
//					for (int i = 0; i < panel_infoCapas.getComponentCount(); i++) {
//						panel_infoCapas.remove(i);
//					}
//				}
			}
		} catch (Exception e) {
			System.err.println(" cleanTabPanel "+ e);
		}
	}
}