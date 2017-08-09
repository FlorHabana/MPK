package Controllers;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

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
import com.esri.map.ArcGISPopupInfo;
import com.esri.map.JMap;
import com.esri.map.Layer;
import com.esri.map.LayerInfo;
import com.esri.map.LayerList;
import com.esri.runtime.ArcGISRuntime;

public class MostrarCapas {
//	LayerList listaLayers =new LayerList();
	
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
	

}