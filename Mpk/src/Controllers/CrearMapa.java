package Controllers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import com.esri.arcgis.carto.TopologyLayer;
import com.esri.arcgis.geodatabase.TopologyErrorFeature;
import com.esri.client.local.ArcGISLocalDynamicMapServiceLayer;
import com.esri.client.local.ArcGISLocalFeatureLayer;
import com.esri.map.JMap;
import com.esri.map.Layer;
import com.esri.map.LayerInitializeCompleteEvent;
import com.esri.map.LayerInitializeCompleteListener;
import com.esri.map.Layer.LayerStatus;
import com.esri.toolkit.legend.JLegend;

import Classes.FileDrop;

import com.esri.map.LayerInfo;

public class CrearMapa {

	private JProgressBar progressBar;
	private boolean isTiledLayerInitialized = false;
	private boolean isDynamicLayerInitialized = false;
	private JComponent contentPane;
	HashMap<String, LayerInfo> listaLayers = new HashMap<String, LayerInfo>();
	String nuevaURL=""; 
	
	
	public JComponent crearMapa (JMap map ) {
		contentPane = createContentPane();
		contentPane.setBackground(Color.BLUE);
		System.out.println("metodo crear mapa");
		 new FileDrop( System.out, contentPane, new FileDrop.Listener()
	        {
			 public void filesDropped( java.io.File[] files )
	            {  
				 System.out.println("Entré al evento");
				 for( int i = 0; i < files.length; i++ )
	                {   try
	                    { 
	                	nuevaURL=  files[i].getCanonicalPath() +"" ;
	                	 System.out.println("nueva "+ nuevaURL);
		             		if (!nuevaURL.equals("")) {
		             			progressBar = createProgressBar(contentPane);
		             			createMap(map, nuevaURL);
		             			contentPane.add(progressBar); 
		             			contentPane.add(map);
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
		return contentPane;
	}

	private void createMap(final JMap map, String url) { 
		updateProgresBarUI("Iniciando servicio de mapa.", true);
		final ArcGISLocalDynamicMapServiceLayer arcGISLocalDynamicMapServiceLayer = new ArcGISLocalDynamicMapServiceLayer(url);
		arcGISLocalDynamicMapServiceLayer.addLayerInitializeCompleteListener(new LayerInitializeCompleteListener() {
			public void layerInitializeComplete(LayerInitializeCompleteEvent e) {
				System.out.println(" LayerInitializeCompleteEvent " + arcGISLocalDynamicMapServiceLayer.getLayers());
				synchronized (progressBar) {
					if (arcGISLocalDynamicMapServiceLayer.getStatus() == LayerStatus.INITIALIZED) {
						listaLayers= arcGISLocalDynamicMapServiceLayer.getLayers();
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
}