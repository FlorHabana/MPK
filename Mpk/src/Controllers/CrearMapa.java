package Controllers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import com.esri.arcgis.carto.ILayer;
import com.esri.arcgis.carto.MapServer;
import com.esri.arcgis.carto.TopologyLayer;
import com.esri.arcgis.datasourcesGDB.AccessWorkspaceFactory;
import com.esri.arcgis.datasourcesGDB.FileGDBScratchWorkspaceFactory;
import com.esri.arcgis.datasourcesGDB.FileGDBWorkspaceFactory;
import com.esri.arcgis.datasourcesGDB.SdeWorkspaceFactory;
import com.esri.arcgis.display.IDisplay;
import com.esri.arcgis.enginecore.PackageFile;
import com.esri.arcgis.geodatabase.FeatureClass;
import com.esri.arcgis.geodatabase.IDataset;
import com.esri.arcgis.geodatabase.IDatasetName;
import com.esri.arcgis.geodatabase.IEnumDataset;
import com.esri.arcgis.geodatabase.IEnumDatasetName;
import com.esri.arcgis.geodatabase.IEnumDatasetProxy;
import com.esri.arcgis.geodatabase.IFeatureClass;
import com.esri.arcgis.geodatabase.IFeatureWorkspace;
import com.esri.arcgis.geodatabase.ITopology;
import com.esri.arcgis.geodatabase.IWorkspace;
import com.esri.arcgis.geodatabase.IWorkspaceFactory;
import com.esri.arcgis.geodatabase.IWorkspaceName;
import com.esri.arcgis.geodatabase.Topology;
import com.esri.arcgis.geodatabase.TopologyErrorFeature;
import com.esri.arcgis.geodatabase.Workspace;
import com.esri.arcgis.geodatabase.WorkspaceFactory;
import com.esri.arcgis.geodatabase.esriDatasetType;
import com.esri.arcgis.geoprocessing.GPTool;
import com.esri.arcgis.geoprocessing.GeoProcessor;
import com.esri.arcgis.geoprocessing.IGeoProcessorResult;
import com.esri.arcgis.geoprocessing.tools.datamanagementtools.ExtractPackage;
import com.esri.arcgis.geoprocessing.tools.datamanagementtools.PackageMap;
import com.esri.arcgis.geoprocessing.tools.datamanagementtools.UncompressFileGeodatabaseData;
import com.esri.arcgis.interop.AutomationException;
import com.esri.arcgis.system.AoInitialize;
import com.esri.arcgis.system.EngineInitializer;
import com.esri.arcgis.system.IFileNames;
import com.esri.arcgis.system.IName;
import com.esri.arcgis.system.IPropertySet;
import com.esri.arcgis.system.ITrackCancel;
import com.esri.arcgis.system.IUID;
import com.esri.arcgis.system.PropertySet;
import com.esri.arcgisruntime.localserver.LocalServer;
import com.esri.client.local.ArcGISLocalDynamicMapServiceLayer;
import com.esri.client.local.ArcGISLocalFeatureLayer;
import com.esri.core.geodatabase.Geodatabase;
import com.esri.core.geodatabase.GeodatabaseFeatureTable;
import com.esri.core.map.CallbackListener;
import com.esri.core.map.Feature;
import com.esri.core.map.FeatureResult;
import com.esri.core.tasks.query.QueryParameters;
import com.esri.map.ArcGISFeatureLayer;
import com.esri.map.FeatureLayer;
import com.esri.map.GraphicsLayer;
import com.esri.map.JMap;
import com.esri.map.Layer;
import com.esri.map.LayerInitializeCompleteEvent;
import com.esri.map.LayerInitializeCompleteListener;
import com.esri.map.LayerList;
import com.esri.map.Layer.LayerStatus;
import com.esri.toolkit.legend.JLegend;
import com.esri.toolkit.overlays.HitTestEvent;
import com.esri.toolkit.overlays.HitTestListener;
import com.esri.toolkit.overlays.HitTestOverlay;

import Classes.FileDrop;

import com.esri.map.LayerInfo;

public class CrearMapa {

	private JProgressBar progressBar;
	private boolean isTiledLayerInitialized = false;
	private boolean isDynamicLayerInitialized = false;
	private JComponent contentPane;
	HashMap<String, LayerInfo> listaLayers = new HashMap<String, LayerInfo>();
	static String nuevaURL=System.getProperty("user.home")+"\\Documents\\ArcGIS\\Untitled.mpk"; 
	com.esri.map.GroupLayer  groupLayer= new com.esri.map.GroupLayer();
	
	public JComponent crearMapa (final JMap map, JButton button ) {
		com.esri.client.local.LocalServer.getInstance().initializeAsync();
		contentPane = createContentPane();
		contentPane.setBackground(Color.BLUE);
		System.out.println("metodo crear mapa");
//		 new FileDrop( System.out, contentPane, new FileDrop.Listener()
//	        {
//			 public void filesDropped( java.io.File[] files )
//	            {  
//				 System.out.println("Entré al evento");
//				 for( int i = 0; i < files.length; i++ )
//	                {   try
//	                    { 
//	                	nuevaURL=  files[i].getCanonicalPath() +"" ;
//	                	 System.out.println("nueva "+ nuevaURL);
//		             		if (!nuevaURL.equals("")) {
//		             			progressBar = createProgressBar(contentPane);
//		             			createMap(map, nuevaURL); 
//		             			contentPane.add(progressBar); 
//		             			contentPane.add(map);
//		             		} else { 
//		             			JOptionPane.showMessageDialog(contentPane, "Arrastre archivo.");
//		             		}
//	                    }   // end try
//	                    catch( java.io.IOException e ) {
//	                    	System.out.println("e "+e.getMessage());
//	                    }
//	                }   // end for: through each dropped file
//	            }   // end filesDropped
//	        });
		 
		progressBar = createProgressBar(contentPane);
		createMap(map, nuevaURL, button);
		contentPane.add(progressBar);
		contentPane.add(map);
		return contentPane;
	}

	private void createMap(final JMap map, final String url, final JButton button) { 
		updateProgresBarUI("Iniciando servicio de mapa.", true);
		//		iniciarArcObjects();
		final ArcGISLocalDynamicMapServiceLayer arcGISLocalDynamicMapServiceLayer = new ArcGISLocalDynamicMapServiceLayer(url);
		arcGISLocalDynamicMapServiceLayer.addLayerInitializeCompleteListener(new LayerInitializeCompleteListener() {
			public void layerInitializeComplete(LayerInitializeCompleteEvent e) {
				System.out.println(" LayerInitializeCompleteEvent " + arcGISLocalDynamicMapServiceLayer.getLayers());
				synchronized (progressBar) {
					if (arcGISLocalDynamicMapServiceLayer.getStatus() == LayerStatus.INITIALIZED) {
						button.setEnabled(true); 
						listaLayers= arcGISLocalDynamicMapServiceLayer.getLayers();
						for (int i = 0; i < listaLayers.size(); i++) {
							LayerInfo layerInfo = (LayerInfo) listaLayers.values().toArray()[i]; 
							ArcGISLocalFeatureLayer arcGISLocalFeatureLayer = new ArcGISLocalFeatureLayer(url+"\\", layerInfo.getId());
							map.getLayers().add(arcGISLocalFeatureLayer);
							arcGISLocalFeatureLayer.addLayerInitializeCompleteListener(new LayerInitializeCompleteListener() {
								public void layerInitializeComplete(LayerInitializeCompleteEvent e) {

								}
							});
						}
						for (int i = 0; i < map.getLayers().size(); i++) {
							if(map.getLayers().get(i).getName()!=null){
								if(map.getLayers().get(i).getName().equals("Layers")){
									map.getLayers().remove(i);
								}
							}
						}
						for (Layer layer : map.getLayers()) {
							groupLayer.add(layer);
						}
						groupLayer.setName("Capas");
						map.getLayers().clear();
						System.out.println("map.getLayers------> "+map.getLayers().size());
						if(map.getLayers().size()==0){
							map.getLayers().add(groupLayer);
						}
						System.out.println("despues de agregar grouplayer map.getLayers------> "+map.getLayers().size());
					}
					if (e.getID() == LayerInitializeCompleteEvent.LOCALLAYERCREATE_ERROR) {
						String errMsg = "Failed to initialize due to " + arcGISLocalDynamicMapServiceLayer.getInitializationError();
						System.out.println(" error " + errMsg);
						int contador =0;
						String error ="";
						for (int i=0; i<errMsg.length(); i++) {
							contador ++;
							error =error +errMsg.charAt(i);
							if (contador ==80) {
								error =error +"\n";
								contador=0;
							}
						}
						JOptionPane.showMessageDialog(contentPane, error, "", JOptionPane.ERROR_MESSAGE);
					}
					isDynamicLayerInitialized = false;
					System.out.println(" isDynamicLayerInitialized "+isDynamicLayerInitialized);
					updateProgresBarUI(null, isDynamicLayerInitialized );
				}
			}
		});
		map.getLayers().add(arcGISLocalDynamicMapServiceLayer);
	}
	
	
	public static void createTopology(JMap map){ 
		try {
			EngineInitializer.initializeEngine();
		    // Open the workspace and the required datasets.
			IWorkspaceFactory workspaceFactory = new FileGDBWorkspaceFactory();
			TopologyLayer topologyLayer = new TopologyLayer();
			UncompressFileGeodatabaseData uncompressFileGeodatabaseData = new  UncompressFileGeodatabaseData();
			uncompressFileGeodatabaseData.setInData(nuevaURL);
			//topologyLayer.draw(ILayer.IID34c20002_4d3c_11d0_92d8_00805f7c28b0, IDisplay.IIDe6bdb002_4d35_11d0_98be_00805f7ced21, ITrackCancel.IID34c20005_4d3c_11d0_92d8_00805f7c28b0);
			
			
//		    IWorkspaceFactory workspaceFactory =new FileGDBWorkspaceFactory();
		} catch (Exception e) {
			System.out.println(" Exception ---------------------------------- "+e.getMessage());
		}

	}
	
	
	public void dibujarCapas(JMap map, JPanel panelMenuCapas) {
		JLegend legend = new JLegend(map);
		legend.setPreferredSize(new Dimension(300, 749));
		legend.setBorder(new LineBorder(new Color(205, 205, 255), 3));
		panelMenuCapas.add(legend, BorderLayout.WEST);
	}

	private void updateProgresBarUI(final String str, final boolean visible) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (str != null) {
					progressBar.setString(str);
				}
				progressBar.setVisible(visible);
			}
		});
	}

	private static JProgressBar createProgressBar(final JComponent parent) {
		final JProgressBar progressBar = new JProgressBar();
		progressBar.setSize(320, 20);
		parent.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				progressBar.setLocation(
						parent.getWidth()/2 - progressBar.getWidth()/2,
						parent.getHeight() - progressBar.getHeight() - 20);
			}
		});
		progressBar.setStringPainted(true);
		progressBar.setIndeterminate(true);
		progressBar.setVisible(false);
		return progressBar;
	}

	public static JLayeredPane createContentPane() {
		JLayeredPane contentPane = new JLayeredPane();
		contentPane.setBounds(100, 100, 1000, 700);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setVisible(true);
		return contentPane;
	}
	
	
	public static void iniciarArcObjects(){ 
		try {
			EngineInitializer.initializeEngine();
		} catch (Exception e) {
			System.out.println(" Exception ---------------------------------- "+e.getMessage());
		}
	}
	
	public void agregarEvento(JMap map) {
		System.out.println(" prueba " + map.getLayers());
		LayerList listLayers = map.getLayers();
		if (listaLayers != null) {
			for (int x= 0; x<listLayers.size(); x++) {
				ArcGISLocalDynamicMapServiceLayer arcGISLocalDynamicMapServiceLayer = (ArcGISLocalDynamicMapServiceLayer) listLayers.get(x);
				HashMap<String, LayerInfo> hashMap = arcGISLocalDynamicMapServiceLayer.getLayers();
				for (int i = 0; i < hashMap.size(); i++) {
					LayerInfo layerInfo = (LayerInfo) hashMap.values().toArray()[i];
					//seleccionarPredio(map, layerInfo);
				}
				System.out.println("arcGISLocalDynamicMapServiceLayer "+arcGISLocalDynamicMapServiceLayer.getLayers());
			}
		}
	}
	public void seleccionarPredio(JMap map, ArcGISLocalFeatureLayer arcGISFeatureLayer) {
		LayerList layerList = map.getLayers();
		HitTestListener listener = SeleccionarPredio();
		final HitTestOverlay selectionOverlay = new HitTestOverlay(arcGISFeatureLayer, listener);
		
		map.addMapOverlay(selectionOverlay);
	}
	
	public HitTestListener SeleccionarPredio() {
		HitTestListener listener = new HitTestListener() {
			public void featureHit(HitTestEvent event) {
				// System.out.println(" event " +event.getSource() );
				HitTestOverlay overlay = (HitTestOverlay) event.getSource();
				// System.out.println(" overlay " +overlay.getHitFeatures() );
				List<Feature> hitFeatures = overlay.getHitFeatures();
				ArcGISFeatureLayer arcGISFeatureLayer = (ArcGISFeatureLayer) overlay.getLayer();
				arcGISFeatureLayer.clearSelection();
				for (Feature feature : hitFeatures) {
					if (arcGISFeatureLayer.isGraphicSelected((int) feature.getId())) {
						arcGISFeatureLayer.unselect((int) feature.getId());
					} else {
						arcGISFeatureLayer.select((int) feature.getId());
						arcGISFeatureLayer.setSelectionColor(Color.BLUE);
					}
				}
			}
		};
		return listener;
	}
	
}