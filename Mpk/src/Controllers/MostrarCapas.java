package Controllers;

import com.esri.client.local.ArcGISLocalDynamicMapServiceLayer;
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
	public void test(){
		
	}
}