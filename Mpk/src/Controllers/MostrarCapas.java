package Controllers;

import java.io.IOException;

import javax.swing.JPanel;

import com.esri.arcgis.datasourcesGDB.AccessWorkspaceFactory;
import com.esri.arcgis.datasourcesGDB.FileGDBWorkspaceFactory;
import com.esri.arcgis.datasourcesGDB.SdeWorkspaceFactory;
import com.esri.arcgis.geodatabase.Workspace;
import com.esri.arcgis.geodatabase.WorkspaceFactory;
import com.esri.arcgis.system.AoInitialize;
import com.esri.arcgis.system.EngineInitializer;
import com.esri.arcgis.system.esriLicenseProductCode;
import com.esri.arcgis.system.esriLicenseStatus;
import com.esri.arcgisruntime.localserver.LocalGeoprocessingService.ServiceType;
import com.esri.client.local.ArcGISLocalDynamicMapServiceLayer;
import com.esri.core.geodatabase.Geodatabase;
import com.esri.core.io.EsriErrorCode;
import com.esri.map.JMap;
import com.esri.map.LayerInfo;
import com.esri.runtime.ArcGISRuntime;

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
	public void cleanPanel(JPanel panel_infoCapas){
		for (int i = 0; i < panel_infoCapas.getComponentCount(); i++) {
			panel_infoCapas.remove(i);
		}
	}
	
	private static void initializeArcGISLicenses(AoInitialize aoInit) {
		try {
			if (aoInit.isProductCodeAvailable(aoInit.initializedProduct())== esriLicenseStatus.esriLicenseAvailable){
				System.out.println("qwqeqweqweqeqwe");
				aoInit.initialize(esriLicenseProductCode.esriLicenseProductCodeEngine);}
			else if (aoInit.isProductCodeAvailable(esriLicenseProductCode.esriLicenseProductCodeEngineGeoDB)==esriLicenseStatus.esriLicenseAvailable)
				aoInit.initialize(esriLicenseProductCode.esriLicenseProductCodeStandard);
			else{
				System.err.println("Could not initialize an Engine or ArcView license. Exiting application.");
				System.exit(-1);
			}
		} catch (Exception e) {e.printStackTrace();}
	}

	public void iniciarApagarServidor(){

	}
}