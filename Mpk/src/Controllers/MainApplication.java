package Controllers;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingConstants;

import com.esri.map.JMap;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import com.esri.arcgis.beans.symbology.SymbologyBean;
import javax.swing.border.LineBorder;
import com.esri.arcgis.beans.toolbar.ToolbarBean;
import com.esri.arcgis.geodatabase.Workspace;
import com.esri.client.local.ArcGISLocalDynamicMapServiceLayer;

public class MainApplication {

	private JFrame frmMpk;
	private JMap map = new JMap();
	JMap jmap=null;
	String url="";
	CrearMapa crearMapa = new CrearMapa();
	JComponent jComponent =null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainApplication window = new MainApplication();
					window.frmMpk.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainApplication() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMpk = new JFrame();
		frmMpk.setTitle("MPK");
		frmMpk.setBounds(100, 100, 1200, 700);
		frmMpk.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel PanelButtons = new JPanel();
		PanelButtons.setBackground(Color.WHITE);
		frmMpk.getContentPane().add(PanelButtons, BorderLayout.NORTH);
		GridBagLayout gbl_PanelButtons = new GridBagLayout();
		gbl_PanelButtons.columnWidths = new int[]{91, 101, 298, 0, 0, 0, 0};
		gbl_PanelButtons.rowHeights = new int[]{0, 0, 23, 0};
		gbl_PanelButtons.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_PanelButtons.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		PanelButtons.setLayout(gbl_PanelButtons);
		
		JButton btnDeleteData = new JButton("Borrar Archivo");
		
		GridBagConstraints gbc_btnDeleteData = new GridBagConstraints();
		gbc_btnDeleteData.insets = new Insets(0, 0, 5, 5);
		gbc_btnDeleteData.anchor = GridBagConstraints.NORTH;
		gbc_btnDeleteData.gridx = 3;
		gbc_btnDeleteData.gridy = 1;
		PanelButtons.add(btnDeleteData, gbc_btnDeleteData);
		
		JButton btnIdentify = new JButton("");
		btnIdentify.setEnabled(false);
		btnIdentify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Identify identify = new Identify(map);
				identify.setVisible(true);
			}
		});
		btnIdentify.setIcon(new ImageIcon(MainApplication.class.getResource("/img/info.png")));
		btnIdentify.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(Color.white, 1), BorderFactory.createLineBorder(Color.white,2)));
		GridBagConstraints gbc_btnIdentify = new GridBagConstraints();
		gbc_btnIdentify.insets = new Insets(0, 0, 5, 0);
		gbc_btnIdentify.gridx = 5;
		gbc_btnIdentify.gridy = 1;
		PanelButtons.add(btnIdentify, gbc_btnIdentify);
		
		JPanel PanelLayers = new JPanel();
		
		frmMpk.getContentPane().add(PanelLayers, BorderLayout.WEST);
//		btnAddData.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				url=txtUrl.getText();
//				frmMpk.getContentPane().add(crearMapa.crearMapa(map, url));
//			}
//		});
		jComponent =crearMapa.crearMapa(map, btnIdentify);
		frmMpk.getContentPane().add(jComponent);
		frmMpk.addWindowListener(new WindowAdapter() {
	      @Override
	      public void windowClosing(WindowEvent windowEvent) {
	        super.windowClosing(windowEvent);
	        map.dispose();
	      }
	    });
		
		
		btnDeleteData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Borrar "+jComponent.getComponentCount());
				map.getLayers().clear();
				int num = jComponent.getComponentCount();
				while(num != 0){
					System.out.println("antes num : " +jComponent.getComponentCount());
					jComponent.remove(jComponent.getComponent(num-1));
					System.out.println("despues num : " +jComponent.getComponentCount());
					num--;
				}
				jComponent.repaint();
				System.out.println("componente "+jComponent.getComponentCount());
			}
		});
		
		
		PanelLayers.setBackground(new Color(240, 248, 255));
		frmMpk.getContentPane().add(PanelLayers, BorderLayout.WEST);
		PanelLayers.setPreferredSize(new Dimension(300, 660));
		PanelLayers.setLayout(new BorderLayout(0, 0));
		crearMapa.dibujarCapas(map, PanelLayers); 
	}

}
