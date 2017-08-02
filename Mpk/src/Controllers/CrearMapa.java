package Controllers;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import com.esri.client.local.ArcGISLocalDynamicMapServiceLayer;
import com.esri.map.JMap;
import com.esri.map.LayerInitializeCompleteEvent;
import com.esri.map.LayerInitializeCompleteListener;

public class CrearMapa {
	
	 private JProgressBar progressBar;
	 private boolean isTiledLayerInitialized = false;
	  private boolean isDynamicLayerInitialized = false;
	  private JComponent contentPane;
	  
	 public JComponent crearMapa (JMap map ) {
		 contentPane = createContentPane();
		 progressBar = createProgressBar(contentPane);
		 createMap(map);
		 
		contentPane.add(progressBar); 
		contentPane.add(map);
		return contentPane;
	}
	
	private void createMap(JMap map) { 
		 updateProgresBarUI("Starting local dynamic map service...", true);
			final ArcGISLocalDynamicMapServiceLayer dynamicLayer = new ArcGISLocalDynamicMapServiceLayer("C:\\Users\\UsuarioJAVA\\Documents\\ArcGIS\\Untitled.mpk");
			dynamicLayer.addLayerInitializeCompleteListener(new LayerInitializeCompleteListener() {
				public void layerInitializeComplete(LayerInitializeCompleteEvent e) {
					System.out.println(" LayerInitializeCompleteEvent "+e.getID());
					synchronized (progressBar) { 
						if (e.getID() == LayerInitializeCompleteEvent.LOCALLAYERCREATE_ERROR) {
							String errMsg = "Failed to initialize due to " + dynamicLayer.getInitializationError();
							System.out.println(" error "+errMsg);
						}
						isDynamicLayerInitialized = true;
						updateProgresBarUI(null, !(isDynamicLayerInitialized && isTiledLayerInitialized));
					}
				}
			});
			map.getLayers().add(dynamicLayer);
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
	 
	 
	  private static JLayeredPane createContentPane() {
		    JLayeredPane contentPane = new JLayeredPane();
		    contentPane.setBounds(100, 100, 1000, 700);
		    contentPane.setLayout(new BorderLayout(0, 0));
		    contentPane.setVisible(true);
		    return contentPane;
		  }
}
