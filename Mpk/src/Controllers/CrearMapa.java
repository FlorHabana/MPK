package Controllers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import com.esri.arcgis.carto.TopologyLayer;
import com.esri.arcgis.datasourcesGDB.FileGDBWorkspaceFactory;
import com.esri.arcgis.geodatabase.IWorkspaceFactory;
import com.esri.arcgis.geoprocessing.tools.datamanagementtools.UncompressFileGeodatabaseData;
import com.esri.arcgis.system.EngineInitializer;
import com.esri.client.local.ArcGISLocalFeatureLayer;
import com.esri.client.local.LayerDetails;
import com.esri.client.local.LocalMapService;
import com.esri.client.local.LocalServiceStartCompleteEvent;
import com.esri.client.local.LocalServiceStartCompleteListener;
import com.esri.client.local.LocalServiceStatus;
import com.esri.core.ags.LayerServiceInfo;
import com.esri.core.ags.MapServiceInfo;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Line;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.Polyline;
import com.esri.core.geometry.Segment;
import com.esri.core.map.Feature;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol.Style;
import com.esri.map.ArcGISDynamicMapServiceLayer;
import com.esri.map.GroupLayer;
import com.esri.map.JMap;
import com.esri.map.LayerInitializeCompleteEvent;
import com.esri.map.LayerInitializeCompleteListener;
import com.esri.map.LayerList;
import com.esri.map.Layer.LayerStatus;
import com.esri.toolkit.legend.JLegend;
import com.esri.toolkit.overlays.HitTestEvent;
import com.esri.toolkit.overlays.HitTestListener;
import com.esri.toolkit.overlays.HitTestOverlay;

import Classes.FileDrop;
import Classes.MyComboBox;

import com.esri.map.LayerInfo;

public class CrearMapa {

	private static JProgressBar progressBar;
//	private boolean isTiledLayerInitialized = false;
	private static boolean isDynamicLayerInitialized = false;
	private JComponent contentPane;
	HashMap<String, LayerInfo> listaLayers = new HashMap<String, LayerInfo>();
	static String nuevaURL;//=System.getProperty("user.home")+"\\Documents\\ArcGIS\\Untitled.mpk"; 
	com.esri.map.GroupLayer  groupLayer= new com.esri.map.GroupLayer();
	Identify identify = null;
	boolean flag=false;
	
	public JComponent crearMapa (final JMap map, final JButton button, final JPanel PanelLayers ) {
		com.esri.client.local.LocalServer.getInstance().initializeAsync();
		contentPane = createContentPane();
		contentPane.setBackground(Color.BLUE);
		System.out.println("metodo crear mapa");
		System.out.println("flag : "+flag);
		new FileDrop( System.out, contentPane, new FileDrop.Listener(){
			public void filesDropped( java.io.File[] files ){  
				System.out.println("Ha entrado al evento");
				flag=true;
				for( int i = 0; i < files.length; i++ ){   
					try{ 
						nuevaURL=  files[i].getCanonicalPath() +"" ;
						System.out.println("nueva "+ nuevaURL);
						if (!nuevaURL.equals("")) {
//							progressBar = createProgressBar(contentPane);
							createMap(map, nuevaURL, button, PanelLayers); 
//							contentPane.add(progressBar); 
//							contentPane.add(map);
						} else {  
							JOptionPane.showMessageDialog(contentPane, "Arrastre archivo.");
						}
					}   // end try
					catch( java.io.IOException e ) {
						System.out.println("e "+e.getMessage());
					}
				}   // end for: through each dropped file
			}   // end filesDropped
		});
		if(flag==false){
			System.out.println("if");
			progressBar = createProgressBar(contentPane);
			createBaseLayer(map);
//			createMap(map, nuevaURL, button, PanelLayers);
			contentPane.add(progressBar); 
			contentPane.add(map);
		}
		return contentPane;
	}
	
	public JMap createBaseLayer(JMap map){
		updateProgresBarUI("Iniciando servicio de mapa.", true);
		final ArcGISDynamicMapServiceLayer bl=new ArcGISDynamicMapServiceLayer(
				"https://services.arcgisonline.com/arcgis/rest/services/World_Street_Map/MapServer");
		bl.addLayerInitializeCompleteListener(new LayerInitializeCompleteListener() {
			public void layerInitializeComplete(LayerInitializeCompleteEvent e) {
				isDynamicLayerInitialized = false;
				updateProgresBarUI(null, isDynamicLayerInitialized );
			}
		});
		bl.setName("Mapa Base");
		map.getLayers().add(bl);
		return map;
	}

	private void createMap(final JMap map, final String url, final JButton button, final JPanel PanelLayers) { 
		updateProgresBarUI("Iniciando mapa.", true);//System.getProperty("user.home")+"\\Documents\\ArcGIS\\Untitled.mpk"
		final LocalMapService localMapService = new LocalMapService(url);
		localMapService.setEnableDynamicLayers(true);
		localMapService.startAsync();
		localMapService.addLocalServiceStartCompleteListener(new LocalServiceStartCompleteListener() {
			public void localServiceStartComplete(LocalServiceStartCompleteEvent e) {
				if (localMapService.getStatus() == LocalServiceStatus.STARTED) {
					for (LayerDetails layerDetails : localMapService.getMapLayers()) {
						final ArcGISLocalFeatureLayer arcGISLocalFeatureLayer = new ArcGISLocalFeatureLayer(url+"\\", layerDetails.getId());
						final Envelope extent = localMapService.getMapServiceInfo().getInitialExtent();
						map.getLayers().add(arcGISLocalFeatureLayer);
						map.setExtent(extent);
						arcGISLocalFeatureLayer.addLayerInitializeCompleteListener(new LayerInitializeCompleteListener() {
							@SuppressWarnings("static-access")
							public void layerInitializeComplete(LayerInitializeCompleteEvent e) { 
								System.out.println(" url " +arcGISLocalFeatureLayer.getUrl()); 
								if (arcGISLocalFeatureLayer.getStatus() == LayerStatus.INITIALIZED) {
									 button.setEnabled(true); 
									 arcGISLocalFeatureLayer.setVisible(habilitarCapa(localMapService, arcGISLocalFeatureLayer.getName()));
								}else {
									if (e.getID() == LayerInitializeCompleteEvent.LOCALLAYERCREATE_ERROR) {
										String errMsg = "Failed to initialize due to " + arcGISLocalFeatureLayer.getInitializationError();
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
										//JOptionPane.showMessageDialog(contentPane, error, "", JOptionPane.ERROR_MESSAGE);
										JOptionPane jOptionPane = new JOptionPane();
										jOptionPane.showMessageDialog(contentPane, error, "", JOptionPane.ERROR_MESSAGE);
										jOptionPane.setBackground(Color.WHITE); 
									}
								}
							}
						});
					}
					isDynamicLayerInitialized = false;
					System.out.println(" isDynamicLayerInitialized "+isDynamicLayerInitialized);
					updateProgresBarUI(null, isDynamicLayerInitialized );
				} 
			}
		});
	}
	
	public boolean habilitarCapa (LocalMapService localMapService, String name) {
		boolean visible = true;
		MapServiceInfo mapServiceInfo = localMapService.getMapServiceInfo(); 
		for (int i=0; i<mapServiceInfo.getMapServiceLayerInfos().length; i++) {
			LayerServiceInfo layerServiceInfo = mapServiceInfo.getMapServiceLayerInfos()[i];
			if (layerServiceInfo.getName().toLowerCase().equals(name.toLowerCase())) {
				visible = layerServiceInfo.isDefaultVisibility();
				break;
			} 
		}
		return visible;
	}
	
	public static void createTopology(JMap map){ 
		try {
			EngineInitializer.initializeEngine();
		    // Open the workspace and the required datasets.
			IWorkspaceFactory workspaceFactory = new FileGDBWorkspaceFactory();
			TopologyLayer topologyLayer = new TopologyLayer();
			UncompressFileGeodatabaseData uncompressFileGeodatabaseData = new  UncompressFileGeodatabaseData();
			uncompressFileGeodatabaseData.setInData(nuevaURL);
			System.out.println("workspaceFactory : "+workspaceFactory+" topologyLayer : "+topologyLayer);
			//topologyLayer.draw(ILayer.IID34c20002_4d3c_11d0_92d8_00805f7c28b0, IDisplay.IIDe6bdb002_4d35_11d0_98be_00805f7ced21, ITrackCancel.IID34c20005_4d3c_11d0_92d8_00805f7c28b0);
			
			
//		    IWorkspaceFactory workspaceFactory =new FileGDBWorkspaceFactory();
		} catch (Exception e) {
			System.out.println(" Exception ---------------------------------- "+e.getMessage());
		}

	}
	
	
	public void dibujarCapas(JMap map, JPanel panelMenuCapas) {
		JLegend legend = new JLegend(map);
		legend.setPreferredSize(new Dimension(300, 749));
		JScrollPane scrollPane= (JScrollPane) legend.getComponent(0);
		JViewport viewport= scrollPane.getViewport();
		viewport.getComponent(0).setBackground(new Color(100, 149, 237));
		legend.setBorder(new LineBorder(new Color(205, 205, 255), 3));
		panelMenuCapas.add(legend, BorderLayout.WEST);
	}

	private static void updateProgresBarUI(final String str, final boolean visible) {
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
		progressBar.setBackground(Color.WHITE);
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
	
	@SuppressWarnings("deprecation")
	public void agregarEvento(JMap map) {
		System.out.println(" prueba " + map.getLayers());
		if (identify !=null) {
			System.out.println("isActive "+ identify.isActive() + " visible " + identify.isVisible());
			if (identify.isVisible()== true ) { 
				identify.dispose();
				identify.hide();
			} 
		}
		LayerList listLayers = map.getLayers();
		eliminarEventoMapa(map); 
		if (listaLayers != null) {
			ArcGISLocalFeatureLayer arcGISLocalFeatureLayers = null;
			for (int x= 0; x<listLayers.size(); x++) {
				if(!listLayers.get(x).getName().equals("Mapa Base")){
					ArcGISLocalFeatureLayer arcGISLocalFeatureLayer = (ArcGISLocalFeatureLayer) listLayers.get(x);
					arcGISLocalFeatureLayers = arcGISLocalFeatureLayer;
					seleccionarPredio(map, arcGISLocalFeatureLayer); 
				}
			}
			seleccionarVariosPredios(map, arcGISLocalFeatureLayers);
		}
	}
	
	public void eliminarEventoMapa (JMap map) { 
		System.out.println("map.getMouseMotionListeners().length "+map.getMouseMotionListeners().length);
		for (int i=0; i<map.getMouseMotionListeners().length; i++) {
			map.removeMouseMotionListener(map.getMouseMotionListeners()[i]);			
		}
	}
	
	private Point inicioArrastre;
	int idgrafic =0, idLinea=0;
	private Point finArrastre;
	Graphic GraphicLinea =null;
	String nameLayer= "";
	final SimpleMarkerSymbol SYM_POINT = new SimpleMarkerSymbol(new Color(255, 4, 4), 10, Style.TRIANGLE);
	SimpleLineSymbol SYM_LINE = new SimpleLineSymbol(new Color(0, 0, 0), 1); 
	ArcGISLocalFeatureLayer arcGISLocalFeatureLayerSelect;
	Graphic lineGraphic = new Graphic(inicioArrastre, SYM_POINT);
	
	public void seleccionarVariosPredios (final JMap map, final ArcGISLocalFeatureLayer arcGISLocalFeatureLayer) {
		map.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unused")
			public void mousePressed(MouseEvent event) {
				inicioArrastre = map.toMapPoint(event.getX(), event.getY());
				Graphic lineGraphic = new Graphic(inicioArrastre, SYM_POINT);
				idLinea=0;
				arcGISLocalFeatureLayerSelect = obtenerLayerSeleccionado(map);
			}
			public void mouseReleased(MouseEvent arg0) {
				if (GraphicLinea != null && arcGISLocalFeatureLayerSelect != null) {
					final boolean visible = arcGISLocalFeatureLayerSelect.isVisible();
					arcGISLocalFeatureLayer.removeGraphic(idLinea); 
					obtenerFeatureSeleccionados (arcGISLocalFeatureLayerSelect,GraphicLinea, idLinea, map );
					final Timer timer = new Timer(); 
			        TimerTask timerTask = new TimerTask() {
			            public void run(){
			            	arcGISLocalFeatureLayerSelect.clearSelection();
			            	timer.cancel(); 
			            	arcGISLocalFeatureLayerSelect.setVisible(visible);  
			            }
			        };
			       timer.scheduleAtFixedRate(timerTask, 800, 800);
				}
			}
		});
		map.addMouseMotionListener(new  MouseMotionAdapter() {
            public void mouseDragged(MouseEvent event) { 
            	if (arcGISLocalFeatureLayer != null) {
            		arcGISLocalFeatureLayer.refresh();            		
            	}
            	finArrastre = map.toMapPoint(event.getX(), event.getY());
            	if (inicioArrastre != null && finArrastre != null) {
                	Polyline linea = new Polyline();
                	Polygon polygon = new Polygon();
                	polygon.startPath(inicioArrastre);
                	
                	Point point = new Point();
                	point.setX(finArrastre.getX()); 
                	point.setY(inicioArrastre.getY()); 
                	polygon.lineTo(point);
                	
                	polygon.lineTo(finArrastre);
                	
                	Point point1 = new Point();
                	point1.setX(inicioArrastre.getX()); 
                	point1.setY(finArrastre.getY()); 
                	polygon.lineTo(point1);
                	
                	Segment segment = new Line();
        			segment.setStart(inicioArrastre);
        			segment.setEnd(finArrastre);  
        			linea.addSegment(segment, false);
        			GraphicLinea  = new Graphic(polygon, SYM_LINE);
        			if (idLinea==0) {
        				if (arcGISLocalFeatureLayer != null) {
        					idLinea = arcGISLocalFeatureLayer.addGraphic(GraphicLinea);
            				nameLayer = arcGISLocalFeatureLayer.getName();
        				}
        			} else { 
        				if (arcGISLocalFeatureLayer != null) {
        					if (arcGISLocalFeatureLayer.getName().toLowerCase().equals(nameLayer.toLowerCase())) {
        						arcGISLocalFeatureLayer.updateGraphic(idLinea, GraphicLinea); 
            				}
        				}
        			}
                }
            }
       });
	}
	
	
	public ArcGISLocalFeatureLayer obtenerLayerSeleccionado (JMap map) {
		String nameLayer ="";
		ArcGISLocalFeatureLayer arcGISLocalFeatureLayers = null; 
		if (identify == null) {  
		}else {
			if (identify.CmbCapas.getSelectedItem() != null) {
				nameLayer = identify.CmbCapas.getSelectedItem()+"";
			}
		}
		LayerList listLayers = map.getLayers();
		if (listLayers != null) {
			for (int x= 0; x<listLayers.size(); x++) {
				if(!listLayers.get(x).getName().equals("Mapa Base")){
					ArcGISLocalFeatureLayer arcGISLocalFeatureLayer = (ArcGISLocalFeatureLayer) listLayers.get(x);
					if (nameLayer.equals("")) {
						if ((x+1) ==listLayers.size()) { 
							arcGISLocalFeatureLayers = arcGISLocalFeatureLayer;		
						}
					} else {
						if (arcGISLocalFeatureLayer.getName().toLowerCase().equals(nameLayer.toLowerCase())) {
							arcGISLocalFeatureLayers = arcGISLocalFeatureLayer;
							break;
						}
					}
				}
			}
		}
		return arcGISLocalFeatureLayers;
	}
	
	@SuppressWarnings("unused")
	public void obtenerFeatureSeleccionados (ArcGISLocalFeatureLayer arcGISFeatureLayer, Graphic graphic, int id, JMap map) {
		Polygon polygonSeleccion = (Polygon) graphic.getGeometry();
		List<Integer> listaInt = new ArrayList<Integer>();
		arcGISFeatureLayer.clearSelection();
		if (arcGISFeatureLayer.getNumberOfGraphics() != 0) {
			for (int i=0; i<arcGISFeatureLayer.getGraphicIDs().length; i++) {
				int x = arcGISFeatureLayer.getGraphicIDs()[i];  
				Graphic graphic2 = arcGISFeatureLayer.getGraphic(x); 
				Polygon polygon = (Polygon) graphic2.getGeometry(); 
				convinaciones(polygonSeleccion, polygon, graphic2.getId(), listaInt);	
			}
			
		}
		if (listaInt.size() !=0 ) {
			long TInicio = System.currentTimeMillis();
			for (int x : listaInt) {
				arcGISFeatureLayer.select(x); 
				arcGISFeatureLayer.setSelectionColor(Color.BLUE);
				arcGISFeatureLayer.setVisible(true); 
			} 
			long TFin = System.currentTimeMillis();
			if (identify == null) {  
				identify = new Identify(map);
				identify.setVisible(true);
				identify.toFront();
			}else {
				if (identify.isVisible() == false) {
					identify = new Identify(map);
					identify.setVisible(true);
					identify.toFront(); 
				}else  {
					identify.toFront();
					identify.itemSeleccionado(arcGISFeatureLayer);
				}
			}
		} else {
			if (identify != null) {
				MostrarCapas mostrarCapas = new MostrarCapas();
				mostrarCapas.cleanTabPanel(identify.tabbedPane); 
				identify.toFront();
			}
		}  
		idLinea= 0;
		GraphicLinea =null;
	}  
	
	public void convinaciones (Polygon polygonSeleccion , Polygon polygon2, long id, List<Integer> listaInt) {
		double xMin =0, xMax= 0, yMin=0, yMax=0;
		for (int i=0; i<polygonSeleccion.getPointCount(); i++) {
			Point point = polygonSeleccion.getPoint(i); 
			if (i==0) {
				yMax = point.getY();
				xMin = point.getX();
			} else if ((i+2) == polygonSeleccion.getPointCount()) {
				yMin = point.getY();
				xMax = point.getX();
			} 
		}  
		//System.out.println("xMin "+xMin +" xMax "+xMax +" yMin "+ yMin +" yMax "+yMax+" 0");
		if (xMin > xMax) {
			double temporal = xMin;
			xMin = xMax;
			xMax = temporal;
		}
		if (yMin > yMax) {
			double temporal = yMin;
			yMin = yMax;
			yMax = temporal;
		}
		//System.out.println("xMin "+xMin +" xMax "+xMax +" yMin "+ yMin +" yMax "+yMax+" 1");
		for (int x=0; x<polygon2.getPointCount(); x++) {
			Point point2 = polygon2.getPoint(x);
			if ((point2.getX() >= xMin && point2.getX() <= xMax) && (point2.getY() >= yMin && point2.getY() <= yMax) ) {
				listaInt.add(Integer.parseInt(id+""));
				break;
			}
		}  
	}
	
	public void seleccionarPredio(JMap map, ArcGISLocalFeatureLayer arcGISFeatureLayer) {
//		LayerList layerList = map.getLayers();
		HitTestListener listener = SeleccionarPredio( map);
		final HitTestOverlay selectionOverlay = new HitTestOverlay(arcGISFeatureLayer, listener);
		map.addMapOverlay(selectionOverlay);
	}
	
	public HitTestListener SeleccionarPredio(final JMap map) {
		HitTestListener listener = new HitTestListener() {
			public void featureHit(HitTestEvent event) {
				HitTestOverlay overlay = (HitTestOverlay) event.getSource();
				List<Feature> hitFeatures = overlay.getHitFeatures();
				final ArcGISLocalFeatureLayer arcGISFeatureLayer = (ArcGISLocalFeatureLayer) overlay.getLayer();
				arcGISFeatureLayer.clearSelection();
				if (arcGISFeatureLayer.isVisible() == true) {
					for (Feature feature : hitFeatures) {
						if (arcGISFeatureLayer.isGraphicSelected((int) feature.getId())) {
							arcGISFeatureLayer.unselect((int) feature.getId());
						} else {
							arcGISFeatureLayer.select((int) feature.getId());
							arcGISFeatureLayer.setSelectionColor(Color.BLUE);
							mostrarInformacionFeature (map, arcGISFeatureLayer, (int) feature.getId());
							final Timer timer = new Timer(); 
					        TimerTask timerTask = new TimerTask() {
					            public void run() 
					            {
					            	arcGISFeatureLayer.clearSelection();
					            	timer.cancel(); 
					            }
					        };
					       timer.scheduleAtFixedRate(timerTask, 800, 800);
						}
					}
				}
			}
		};
		return listener;
	}
	
	
	public void mostrarInformacionFeature (final JMap map, ArcGISLocalFeatureLayer arcGISLocalFeatureLayer, int id) {
		if (identify == null) {  
			identify = new Identify(map);
			identify.setVisible(true);
			identify.toFront();
			//eventoCombo(map); 
		}else {
			//System.out.println(" identify "+identify.isVisible());
			if (identify.isVisible() == false) {
				identify = new Identify(map);
				identify.setVisible(true);
				identify.toFront();
			}else  {
				identify.toFront();
				identify.itemSeleccionado(arcGISLocalFeatureLayer);
			}
		}
	}
	
	public String addBaseLayer(int num){
		String nameBaseLayer ="";
		String [] arrayBaseLayer={"https://services.arcgisonline.com/arcgis/rest/services/World_Imagery/MapServer",
				"https://services.arcgisonline.com/arcgis/rest/services/World_Physical_Map/MapServer",
				"https://services.arcgisonline.com/arcgis/rest/services/World_Shaded_Relief/MapServer",
				"https://services.arcgisonline.com/arcgis/rest/services/World_Street_Map/MapServer",
				"https://services.arcgisonline.com/arcgis/rest/services/World_Terrain_Base/MapServer",
				"https://services.arcgisonline.com/arcgis/rest/services/World_Topo_Map/MapServer"};
		nameBaseLayer= arrayBaseLayer[num];
		return nameBaseLayer;
	}
	
	public static void changeBaseLayer(JMap map, String nameBaseLayer) {
		updateProgresBarUI("Iniciando servicio de mapa.", true);
		ArcGISDynamicMapServiceLayer baselayer=new ArcGISDynamicMapServiceLayer(nameBaseLayer);
		baselayer.setName("Mapa Base");
		GroupLayer groupLayer = new GroupLayer();
		for (int i = 0; i < map.getLayers().size(); i++) {
			groupLayer.add(map.getLayers().get(i));
		}
		try {
			for (int i = 0; i < groupLayer.size(); i++) {
				if(!groupLayer.get(i).getName().equals(null)){
					if(groupLayer.get(i).getName().equals("Mapa Base")){
						groupLayer.remove(i);
					}
				}
			}
			map.getLayers().clear();
			map.getLayers().add(baselayer);
			for (int i = 0; i < groupLayer.size(); i++) {
				map.getLayers().add(groupLayer.get(i));
			}
			baselayer.addLayerInitializeCompleteListener(new LayerInitializeCompleteListener() {
				public void layerInitializeComplete(LayerInitializeCompleteEvent e) {
					isDynamicLayerInitialized = false;
					updateProgresBarUI(null, isDynamicLayerInitialized );
				}
			});
		} catch (Exception e) {
			System.out.println("Exception BaseLayer : "+e.getMessage());
		}
	}
	
	public void eventChangeBaseLayer(MyComboBox myComboBox, JMap map){
		System.out.println("Se ha seleccionado un elemento..."+myComboBox.getSelectedItem());
		String nameBaseLayer=addBaseLayer((Integer) myComboBox.getSelectedItem());
		changeBaseLayer(map, nameBaseLayer);
	}
	
}