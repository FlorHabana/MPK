package Controllers;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.esri.client.local.ArcGISLocalFeatureLayer;
import com.esri.core.geometry.Polygon;
import com.esri.core.map.CallbackListener;
import com.esri.core.map.Feature;
import com.esri.core.map.FeatureResult;
import com.esri.core.map.Field;
import com.esri.core.map.Graphic;
import com.esri.core.tasks.query.QueryParameters;
import com.esri.core.tasks.query.QueryTask;
import com.esri.map.GroupLayer;
import com.esri.map.JMap;
import com.esri.map.Layer;
import com.esri.toolkit.legend.JLegend;

import Classes.FondoModal;
import Classes.TablaRenderizadorCliente;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import javax.swing.JTree;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.FlowLayout;
import javax.swing.border.EtchedBorder;
import java.awt.Toolkit;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Window.Type;

public class Identify extends JFrame {
	MostrarCapas mostrarCapas = new MostrarCapas();
	
//	private JPanel contentPane;
	@SuppressWarnings("rawtypes")
	JComboBox CmbCapas = new JComboBox();
	JPanel panel = new JPanel();
	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private final JPanel panel_1 = new JPanel();
	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "serial" })
	public Identify(final JMap map) {
		setType(Type.POPUP);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Identify.class.getResource("/img/earth-globe.png")));
		setResizable(false);
		setBounds(100, 100, 566, 482);
		setLocationRelativeTo(null);
		
		FondoModal fondoModal = new FondoModal();
		getContentPane().add(fondoModal);
		
		JLabel lblIdentify = new JLabel("Identificar de : ");
		lblIdentify.setForeground(Color.WHITE);
		CmbCapas.setBackground(Color.WHITE);
		CmbCapas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obtenerFeatureSeleccionados(map);
			}
		});
		for(String name : mostrarCapas.llenarCombo(map)){
			CmbCapas.addItem(name);
		}
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		tabbedPane.setBackground(Color.WHITE);
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
	
		panel.setBackground(new Color(0f, 0f, 0f, 0f));
		panel_1.setBackground(new Color(0f, 0f, 0f, 0f)); 
		
		panel_1.setBorder(null);
		GroupLayout gl_fondoModal = new GroupLayout(fondoModal);
		gl_fondoModal.setHorizontalGroup(
			gl_fondoModal.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_fondoModal.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_fondoModal.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panel, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
						.addComponent(tabbedPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE))
					.addGap(10))
		);
		gl_fondoModal.setVerticalGroup(
			gl_fondoModal.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_fondoModal.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
					.addContainerGap())
		);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 513, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(5)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 42, Short.MAX_VALUE)
					.addContainerGap())
		);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(145)
					.addComponent(lblIdentify)
					.addGap(18)
					.addComponent(CmbCapas, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
					.addGap(151))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(8)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(CmbCapas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblIdentify))
					.addContainerGap(19, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		fondoModal.setLayout(gl_fondoModal);
	}

	public void obtenerFeatureSeleccionados(final JMap map){
		mostrarCapas.cleanTabPanel(tabbedPane); 
		tabbedPane.updateUI();
		for (int i = 0; i < map.getLayers().size(); i++) {
			GroupLayer groupLayer = (GroupLayer)  map.getLayers().get(i);
			Layer[] layer= groupLayer.getLayers();
			for (int j = 0; j < layer.length; j++) {
				ArcGISLocalFeatureLayer arcGISLocalFeatureLayer = (ArcGISLocalFeatureLayer) layer[j];
				if(CmbCapas.getSelectedItem().equals(arcGISLocalFeatureLayer.getName())){
					if (arcGISLocalFeatureLayer.getSelectionIDs().length >0) {
						crearTree(arcGISLocalFeatureLayer); 
					}
				}
			}
		}
	}
	
	public void itemSeleccionado (ArcGISLocalFeatureLayer arcGISLocalFeatureLayer) {
		int contador =0;
		if (CmbCapas.getSelectedItem() != null) {
			if (arcGISLocalFeatureLayer.getSelectionIDs().length >0) {
				if (contador ==0) {
					System.out.println("namelAYER............. "+arcGISLocalFeatureLayer.getName());
					CmbCapas.setSelectedItem(arcGISLocalFeatureLayer.getName());
					contador =1;
				}
			}
		}
	}
	
	public void crearTree (ArcGISLocalFeatureLayer arcGISLocalFeatureLayer) {
		try {
			int[] idSeleccionados = arcGISLocalFeatureLayer.getSelectionIDs();
			for (int id: idSeleccionados) {
				Graphic graphic =arcGISLocalFeatureLayer.getGraphic(id);
				final JTable table = new JTable(){
					public boolean isCellEditable(int rowIndex, int vColIndex) {
						return false;
					}
				};
				table.setColumnSelectionAllowed(true);
				table.setCellSelectionEnabled(true);
		        
				table.setBorder(new LineBorder(new Color(30, 144, 255), 1, true));
				JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				//scrollPane.add(table);
				tabbedPane.add(""+graphic.getUid(), scrollPane);
				obtenerInformacion(arcGISLocalFeatureLayer, graphic.getUid(), table);
				 
				//System.out.println("idGraphic ---------- " + graphic.getUid() +" name " + arcGISLocalFeatureLayer.getName() + " tab " + tabbedPane.getComponentCount()+" select " +idSeleccionados.length);
			}
		} catch (Exception e) { 
			System.out.println("crearTree e:  "+e.getMessage());
		}
	}
	
	public void obtenerInformacion (ArcGISLocalFeatureLayer arcGISLocalFeatureLayer, final int id, final JTable table) {
		Field[] fields = arcGISLocalFeatureLayer.getFields();
		String Campo="OBJECTID";
		for (int x=0; x<fields.length; x++) {  
			Field fiel = fields[x];
			if (x==0) {
				Campo =fiel.getName();
			}
		}
		
		QueryTask queryTask = new QueryTask(arcGISLocalFeatureLayer.getUrl());
		QueryParameters query = new QueryParameters();
		query.setReturnGeometry(true);
		query.setOutFields(new String[] {"*"});
		query.setWhere(Campo+"="+id  );
		queryTask.execute(query, new CallbackListener<FeatureResult>() {
			public void onCallback(FeatureResult featureResult) {
				try {
					if (featureResult.featureCount() < 1) {
						mostrarMensaje(table); 
						return;
					} 
					mostrarInformacion(featureResult, id, table);
				} catch (Exception e) {
					System.err.println(" onCallback " + e.getMessage());
				}
			}
			public void onError(Throwable e) {
				System.err.println(" onError  " + e.getMessage());
			}
			
		});
	}
	
	@SuppressWarnings("serial")
	public void mostrarInformacion (FeatureResult featureResult, int id, JTable table) {
		//System.out.println(" mostrarInformacion .............");
		Object[][] datosFeature= new Object[][]{};
		for (Object record : featureResult) {
			Feature feature = (Feature) record; 
			Polygon polygon = (Polygon) feature.getGeometry();
			for (int i=0; i<polygon.getPointCount(); i++) {
			}
			Map<String, Object> hasResultado = feature.getAttributes();
			datosFeature= new Object[hasResultado.size()][2];
			for (int i = 0; i < datosFeature.length; i++) {
				String nombreCampo = (String) hasResultado.keySet().toArray()[i]+"";
				String valorCampo = hasResultado.values().toArray()[i]+"";
				if (nombreCampo.toLowerCase().contains("fecha") || nombreCampo.toLowerCase().contains("date")) {
					 if (!valorCampo.equals("") && valorCampo != null && !valorCampo.equals("null")) { 
						long time = Long.parseLong(valorCampo)/1000;
						String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date (time*1000));
						valorCampo = date+ ""; 
					 }
				}
				if (valorCampo.toLowerCase().equals("null")) {
					valorCampo =""; 
				}
				
				datosFeature[i][0]= nombreCampo;
				datosFeature[i][1] =valorCampo;
			}
		}
		table.setModel(new DefaultTableModel(
				datosFeature,
				new String[] {
					"Campo", "Dato"
				}
			) {
				@SuppressWarnings("rawtypes")
				Class[] columnTypes = new Class[] {
					String.class, String.class
				};
				@SuppressWarnings({ "rawtypes", "unchecked" })
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
		TablaRenderizadorCliente renderizador = new TablaRenderizadorCliente();
		table.setDefaultRenderer(String.class, renderizador);
	}
	
	@SuppressWarnings("serial")
	public void mostrarMensaje(JTable table) {
		table.setModel(new DefaultTableModel(
				new Object[][]{
					{"No se encontro información."}
				},
				new String[] {
					"Campo"
				}
			) {
				@SuppressWarnings("rawtypes")
				Class[] columnTypes = new Class[] {
					String.class, String.class
				};
				@SuppressWarnings({ "rawtypes", "unchecked" })
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
	}
}
