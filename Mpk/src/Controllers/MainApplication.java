package Controllers;

import AppPackage.AnimationClass;
import Classes.FondoModal;
import UpperEssential.UpperEssentialLookAndFeel;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

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
import java.awt.Toolkit;
import javax.swing.border.BevelBorder;
import java.awt.CardLayout;
import javax.swing.JComboBox;
import java.awt.FlowLayout;

public class MainApplication {

	private JFrame frmMpk;
	private JMap map = new JMap();
	String url="";
	CrearMapa crearMapa = new CrearMapa();
	JComponent jComponent =null;
	JPanel PanelLayers = new JPanel();
	FondoModal fondoModal = new FondoModal();
	static AnimationClass animationClass= new AnimationClass();
    static AnimationClass animationClasss= new AnimationClass();
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//UIManager.setLookAndFeel(new UpperEssentialLookAndFeel());
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
		frmMpk.getContentPane().setBackground(Color.WHITE);
		frmMpk.setIconImage(Toolkit.getDefaultToolkit().getImage(MainApplication.class.getResource("/img/earth-globe.png")));
		frmMpk.setTitle("MPK");
		frmMpk.setBounds(100, 100, 1200, 700);
		frmMpk.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Dimension d= frmMpk.getSize();
		
		JPanel PanelButtons =fondoModal;
		//PanelButtons.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(30, 144, 255), new Color(100, 149, 237), new Color(30, 144, 255), new Color(100, 149, 237)));
		//PanelButtons.setBackground(Color.WHITE); 
		System.out.println("antes de fondo modal ----------------------------- ");
		frmMpk.getContentPane().add(PanelButtons, BorderLayout.NORTH);
		
		
//		JPanel panelSlider= new JPanel();
//        panelSlider.setPreferredSize(new Dimension((int) d.getWidth(), 100));
//        frmMpk.getContentPane().add(panelSlider, BorderLayout.NORTH);
        
        JLabel lblImagen1 = new JLabel();
        lblImagen1.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/img/fondoWorld.png")));
        
        JLabel lblImagen2 = new JLabel("");
        lblImagen2.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/img/world2.png")));
//        GroupLayout gl_panelSlider = new GroupLayout(panelSlider);
//        gl_panelSlider.setHorizontalGroup(
//        	gl_panelSlider.createParallelGroup(Alignment.LEADING)
//        		.addGroup(gl_panelSlider.createSequentialGroup()
//        			.addGap(0, 0, Short.MAX_VALUE)
//        			.addComponent(lblImagen1, GroupLayout.PREFERRED_SIZE, 1200, GroupLayout.PREFERRED_SIZE)
//        			.addPreferredGap(ComponentPlacement.RELATED)
//        			.addComponent(lblImagen2)
//        			.addGap(1232))
//        );
//        gl_panelSlider.setVerticalGroup(
//        	gl_panelSlider.createParallelGroup(Alignment.LEADING)
//        		.addGroup(gl_panelSlider.createSequentialGroup()
//        			.addGroup(gl_panelSlider.createParallelGroup(Alignment.LEADING, false)
//        				.addComponent(lblImagen1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
//        				.addComponent(lblImagen2))
//        			.addGap(0))
//        );
//        panelSlider.setLayout(gl_panelSlider);
		
		PanelLayers.setBackground(new Color(240, 248, 255));
		frmMpk.getContentPane().add(PanelLayers, BorderLayout.WEST);
		PanelLayers.setPreferredSize(new Dimension(300, 660));
		PanelLayers.setLayout(new BorderLayout(0, 0));
		
		frmMpk.getContentPane().add(PanelLayers, BorderLayout.WEST);
		fondoModal.setLayout(null);
		
		JPanel panel = new JPanel();
		//panel.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(30, 144, 255), new Color(30, 144, 255), new Color(30, 144, 255), new Color(30, 144, 255)));
		panel.setBounds(0, 0, 1184, 61);
		panel.setBackground(new Color(0f, 0f, 0f, 0f));
		panel.setSize(new Dimension(1183, 61));  
		fondoModal.add(panel); 
		
		JButton btnBtneliminar = new JButton("");
//		btnBtneliminar.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(Color.white, 1), BorderFactory.createLineBorder(Color.white,2)));
//		btnBtneliminar.setBackground(Color.WHITE);
		//btnBtneliminar.setToolTipText("Borrar Archivo");
		btnBtneliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
		btnBtneliminar.setIcon(new ImageIcon(MainApplication.class.getResource("/img/garbage.png")));
		
		JButton btnBtninformacion = new JButton("");
		btnBtninformacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearMapa.agregarEvento(map);
			}
		});
		//btnBtninformacion.setToolTipText("Informaci\u00F3n");
//		btnBtninformacion.setBackground(Color.WHITE);
		btnBtninformacion.setEnabled(false);
		btnBtninformacion.setIcon(new ImageIcon(MainApplication.class.getResource("/img/info24.png")));
//		btnBtninformacion.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(Color.white, 1), BorderFactory.createLineBorder(Color.white,2)));
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(465)
					.addComponent(btnBtneliminar)
					.addGap(18)
					.addComponent(btnBtninformacion)
					.addContainerGap(587, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnBtneliminar)
						.addComponent(btnBtninformacion))
					.addContainerGap(17, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		GroupLayout gl_PanelButtons = new GroupLayout(PanelButtons);
		gl_PanelButtons.setHorizontalGroup(
			gl_PanelButtons.createParallelGroup(Alignment.LEADING)
				.addGap(0, 1184, Short.MAX_VALUE)
		);
		gl_PanelButtons.setVerticalGroup(
			gl_PanelButtons.createParallelGroup(Alignment.LEADING)
				.addGap(0, 61, Short.MAX_VALUE)
		);
		PanelButtons.setLayout(gl_PanelButtons);
		
		jComponent =crearMapa.crearMapa(map, btnBtninformacion, PanelLayers);
		frmMpk.getContentPane().add(jComponent);
		frmMpk.addWindowListener(new WindowAdapter() {
	      @Override
	      public void windowClosing(WindowEvent windowEvent) {
	        super.windowClosing(windowEvent);
	        map.dispose();
	      }
	    });
		crearMapa.dibujarCapas(map, PanelLayers);
		
		slideShow(lblImagen1, lblImagen2);
	}
	
	public static void slideShow(final JLabel lblImagen1, final JLabel lblImagen2){
    	new Thread(){
    		int count;
    		@Override
    		public void run(){
    			try {
					while(true){
						switch (count) {
						case 0:
							Thread.sleep(20);
							animationClass.jLabelXLeft(0,-1205, 20, 1, lblImagen1);
							count=1;
							break;
						case 1:
							Thread.sleep(20);
							if (lblImagen1.getX() < 0) {
								if (lblImagen1.getX() == -1205) {
									lblImagen1.setBounds(1205, 0, lblImagen1.getWidth(), lblImagen1.getHeight()); 
									animationClass.jLabelXLeft(1205,-1205, 20, 1, lblImagen1);
								} 
								if (lblImagen2.getX() == -1206) {
									lblImagen2.setBounds(1206, 0, lblImagen2.getWidth(), lblImagen2.getHeight()); 
								}
								animationClasss.jLabelXLeft(1206,-1206, 20, 1, lblImagen2);
							}
							count=1;
							break;
						default:
							break;
						}
					}
				} catch (Exception e) {
					System.out.println("e : "+ e.getMessage());
				}
    		}
    	}.start();
    }
}
