package Controllers;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
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

public class MainApplication {

	private JFrame frmMpk;
	private JTextField txtUrl;
	private JMap map = new JMap();
	CrearMapa crearMapa = new CrearMapa();
	
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
		gbl_PanelButtons.columnWidths = new int[]{91, 101, 298, 0, 0};
		gbl_PanelButtons.rowHeights = new int[]{0, 0, 23, 0};
		gbl_PanelButtons.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_PanelButtons.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		PanelButtons.setLayout(gbl_PanelButtons);
		
		JLabel lblUrl = new JLabel("Ingresar URL:");
		GridBagConstraints gbc_lblUrl = new GridBagConstraints();
		gbc_lblUrl.anchor = GridBagConstraints.EAST;
		gbc_lblUrl.insets = new Insets(0, 0, 5, 5);
		gbc_lblUrl.gridx = 1;
		gbc_lblUrl.gridy = 1;
		PanelButtons.add(lblUrl, gbc_lblUrl);
		
		txtUrl = new JTextField();
		GridBagConstraints gbc_txtUrl = new GridBagConstraints();
		gbc_txtUrl.insets = new Insets(0, 0, 5, 5);
		gbc_txtUrl.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUrl.gridx = 2;
		gbc_txtUrl.gridy = 1;
		PanelButtons.add(txtUrl, gbc_txtUrl);
		txtUrl.setColumns(10);
		
		JButton btnAddData = new JButton("Agregar datos");
		GridBagConstraints gbc_btnAddData = new GridBagConstraints();
		gbc_btnAddData.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddData.anchor = GridBagConstraints.NORTH;
		gbc_btnAddData.gridx = 3;
		gbc_btnAddData.gridy = 1;
		PanelButtons.add(btnAddData, gbc_btnAddData);
		
		JPanel PanelLayers = new JPanel();
		frmMpk.getContentPane().add(PanelLayers, BorderLayout.WEST);
		
		frmMpk.add(crearMapa.crearMapa(map));
		frmMpk.addWindowListener(new WindowAdapter() {
	      @Override
	      public void windowClosing(WindowEvent windowEvent) {
	        super.windowClosing(windowEvent);
	        map.dispose();
	      }
	    });
		
		PanelLayers.setBackground(new Color(240, 248, 255));
		frmMpk.getContentPane().add(PanelLayers, BorderLayout.WEST);
		PanelLayers.setPreferredSize(new Dimension(300, 660));
		PanelLayers.setLayout(new BorderLayout(0, 0));
	}

}
