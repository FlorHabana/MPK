package Controllers;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.esri.client.local.ArcGISLocalDynamicMapServiceLayer;
import com.esri.map.JMap;
import com.esri.toolkit.JLayerTree;
import com.esri.toolkit.legend.JLegend;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;

public class Identify extends JFrame {
	MostrarCapas mostrarCapas = new MostrarCapas();
	
	private JPanel contentPane;
	JComboBox CmbCapas = new JComboBox();
	private BorderLayout bl_panel_infoCapas = new BorderLayout();
	JPanel panel_infoCapas = new JPanel(bl_panel_infoCapas);
	JPanel panel = new JPanel();
	int num=0;

	/**
	 * Create the frame.
	 */
	public Identify(final JMap map) {
		setBounds(100, 100, 400, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		setResizable(false);
		
		contentPane.add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 5, 10, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblIdentify = new JLabel("Identificar de : ");
		GridBagConstraints gbc_lblIdentify = new GridBagConstraints();
		gbc_lblIdentify.anchor = GridBagConstraints.EAST;
		gbc_lblIdentify.insets = new Insets(0, 0, 5, 5);
		gbc_lblIdentify.gridx = 0;
		gbc_lblIdentify.gridy = 1;
		panel.add(lblIdentify, gbc_lblIdentify);
		
		CmbCapas.setBackground(Color.WHITE);
		GridBagConstraints gbc_CmbCapas = new GridBagConstraints();
		gbc_CmbCapas.insets = new Insets(0, 0, 5, 0);
		gbc_CmbCapas.fill = GridBagConstraints.HORIZONTAL;
		gbc_CmbCapas.gridx = 1;
		gbc_CmbCapas.gridy = 1;
		for(String name : mostrarCapas.llenarCombo(map)){
			CmbCapas.addItem(name);
		}
		
		CmbCapas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				llenarLista(map);
				System.out.println("Cambio en el combo "+num);
				num+=1;
			}
		});
		panel.add(CmbCapas, gbc_CmbCapas);
		
		panel_infoCapas.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel_infoCapas = new GridBagConstraints();
		gbc_panel_infoCapas.gridwidth = 2;
		gbc_panel_infoCapas.insets = new Insets(0, 0, 5, 5);
		gbc_panel_infoCapas.fill = GridBagConstraints.BOTH;
		gbc_panel_infoCapas.gridx = 0;
		gbc_panel_infoCapas.gridy = 3;
		panel.add(panel_infoCapas, gbc_panel_infoCapas);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.control);
		contentPane.add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 40, 30, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblEtiqueta = new JLabel("Ubicaci\u00F3n : ");
		lblEtiqueta.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblEtiqueta = new GridBagConstraints();
		gbc_lblEtiqueta.insets = new Insets(0, 0, 5, 5);
		gbc_lblEtiqueta.gridx = 1;
		gbc_lblEtiqueta.gridy = 1;
		panel_1.add(lblEtiqueta, gbc_lblEtiqueta);
		
		JLabel lblLocation = new JLabel("New label");
		GridBagConstraints gbc_lblLocation = new GridBagConstraints();
		gbc_lblLocation.insets = new Insets(0, 0, 5, 5);
		gbc_lblLocation.gridx = 2;
		gbc_lblLocation.gridy = 1;
		panel_1.add(lblLocation, gbc_lblLocation);
		
		JLabel lblNewLabel = new JLabel("*");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 7;
		gbc_lblNewLabel.gridy = 1;
		panel_1.add(lblNewLabel, gbc_lblNewLabel);
		
		JTextArea txtInfo = new JTextArea();
		txtInfo.setEditable(false);
		txtInfo.setColumns(30);
		GridBagConstraints gbc_txtInfo = new GridBagConstraints();
		gbc_txtInfo.gridwidth = 7;
		gbc_txtInfo.gridheight = 7;
		gbc_txtInfo.insets = new Insets(0, 0, 5, 0);
		gbc_txtInfo.fill = GridBagConstraints.BOTH;
		gbc_txtInfo.gridx = 1;
		gbc_txtInfo.gridy = 2;
		panel_1.add(txtInfo, gbc_txtInfo);
		
		JLabel lblNewLabel_1 = new JLabel("1 Caracter\u00EDstica identificada");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridwidth = 2;
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 9;
		panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);
	}

	public void llenarLista(final JMap map){
		for (int i = 0; i < map.getLayers().size(); i++) {
			ArcGISLocalDynamicMapServiceLayer layer = (ArcGISLocalDynamicMapServiceLayer) map.getLayers().get(i);
			for (int j = 0; j < layer.getLayersList().size(); j++) {
				if(CmbCapas.getSelectedItem().equals(layer.getSubLayer(j).getName())){
					DefaultMutableTreeNode padre = new DefaultMutableTreeNode(layer.getSubLayer(j).getName());
					DefaultMutableTreeNode capa = new DefaultMutableTreeNode(layer.getSubLayer(j).getChildLayer(0));
					padre.add(capa);
					DefaultTreeModel modelo = new DefaultTreeModel(padre);

					JTree jTree = new JTree(modelo);
					JScrollPane sp = new JScrollPane(jTree);
					sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
					sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
					panel_infoCapas.add(BorderLayout.NORTH, sp);
					panel_infoCapas.updateUI();
					System.out.println("Después de actualizar "+num);
				}
			}
		}
	}
}
