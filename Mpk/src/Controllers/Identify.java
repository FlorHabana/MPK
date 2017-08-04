package Controllers;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.esri.map.JMap;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Identify extends JFrame {
	MostrarCapas mostrarCapas = new MostrarCapas();
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 *
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Identify frame = new Identify();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public Identify( JMap map) {
		setBounds(100, 100, 400, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JTextArea txtLista = new JTextArea();
		txtLista.setEditable(false);
		txtLista.setRows(8);
		
		JLabel lblIdentify = new JLabel("Identificar de : ");
		GridBagConstraints gbc_lblIdentify = new GridBagConstraints();
		gbc_lblIdentify.anchor = GridBagConstraints.EAST;
		gbc_lblIdentify.insets = new Insets(0, 0, 5, 5);
		gbc_lblIdentify.gridx = 0;
		gbc_lblIdentify.gridy = 1;
		panel.add(lblIdentify, gbc_lblIdentify);
		
		JComboBox CmbCapas = new JComboBox();
		CmbCapas.setBackground(Color.WHITE);
		GridBagConstraints gbc_CmbCapas = new GridBagConstraints();
		gbc_CmbCapas.insets = new Insets(0, 0, 5, 0);
		gbc_CmbCapas.fill = GridBagConstraints.HORIZONTAL;
		gbc_CmbCapas.gridx = 1;
		gbc_CmbCapas.gridy = 1;
		for(String name : mostrarCapas.llenarCombo(map)){
			CmbCapas.addItem(name);
		}
		panel.add(CmbCapas, gbc_CmbCapas);
		
		mostrarCapas.llenarLista(CmbCapas, map, txtLista);
		
		JScrollPane scrollPane = new JScrollPane(txtLista);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		panel.add(scrollPane, gbc_scrollPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.control);
		contentPane.add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 40, 30, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblEtiqueta = new JLabel("Ubicación : ");
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
		
		JLabel lblNewLabel_1 = new JLabel("1 Característica identificada");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridwidth = 2;
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 9;
		panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);
	}

}
