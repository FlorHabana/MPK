package Controllers;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.esri.client.local.ArcGISLocalFeatureLayer;
import com.esri.core.geometry.Polygon;
import com.esri.core.map.CallbackListener;
import com.esri.core.map.Feature;
import com.esri.core.map.FeatureResult;
import com.esri.core.map.Field;
import com.esri.core.map.Graphic;
import com.esri.core.tasks.query.QueryParameters;
import com.esri.core.tasks.query.QueryTask;
import com.esri.map.JMap;

import UpperEssential.UpperEssentialLookAndFeel;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.UIManager;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.BevelBorder;
import java.awt.Toolkit;
import java.awt.Dialog.ModalExclusionType;

@SuppressWarnings("serial")
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Identify(final JMap map) {
		getContentPane().setBackground(Color.WHITE);
		try {
			UIManager.setLookAndFeel(new UpperEssentialLookAndFeel());
		//UIManager.setLookAndFeel("UpperEssential.UpperEssentialLookAndFeel");
		} catch (Exception e) {
			System.out.println(" identify " + e);
		}
		setType(Type.POPUP);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Identify.class.getResource("/img/earth-globe.png")));
		setResizable(false);
		setBounds(100, 100, 566, 482);
		setLocationRelativeTo(null);
		
//		FondoModal fondoModal = new FondoModal();
//		getContentPane().add(fondoModal);
		
		JLabel lblIdentify = new JLabel("Identificar de : ");
		lblIdentify.setForeground(Color.BLACK);
		
		CmbCapas = new JComboBox();
		//CmbCapas.setModel(new DefaultComboBoxModel(new String[] {"BUTTON1"}));
		CmbCapas.setBackground(Color.WHITE);
		CmbCapas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				obtenerFeatureSeleccionados(map);
			}
		});
		for(String name : mostrarCapas.llenarCombo(map)){
			int existe =0;
			if (CmbCapas.getItemCount() != 0) {
				for (int i=0; i<CmbCapas.getItemCount(); i++) {
					if (CmbCapas.getItemAt(i).equals(name)) {
						 existe =1;
						 break;
					}
				}
			}
			if (existe ==0) {
				CmbCapas.addItem(name);				
			}
		}
		CmbCapas.setSelectedIndex(-1);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		tabbedPane.setBackground(Color.WHITE);
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
	
		panel.setBackground(Color.WHITE);
		panel_1.setBackground(Color.WHITE); 
		
		panel_1.setBorder(null);
		GroupLayout gl_fondoModal = new GroupLayout(getContentPane());
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
		getContentPane().setLayout(gl_fondoModal);
	}

	@SuppressWarnings("unchecked")
	public void obtenerFeatureSeleccionados(final JMap map){
		mostrarCapas.cleanTabPanel(tabbedPane); 
		tabbedPane.updateUI();
		for (int i = 0; i < map.getLayers().size(); i++) {
			if(!map.getLayers().get(i).getName().equals("Mapa Base")){
				ArcGISLocalFeatureLayer arcGISLocalFeatureLayer = (ArcGISLocalFeatureLayer) map.getLayers().get(i);
				if (CmbCapas.getSelectedItem() == null) {
					if (arcGISLocalFeatureLayer.getSelectionIDs().length >0){
						CmbCapas.setSelectedItem(arcGISLocalFeatureLayer.getName());
						int existe =0;
						if (CmbCapas.getItemCount() != 0) {
							for (int x=0; x<CmbCapas.getItemCount(); x++) {
								if (CmbCapas.getItemAt(x).equals(arcGISLocalFeatureLayer.getName())) {
									 existe =1;
									 break;
								}
							}
						}
						if (existe ==0) {
							CmbCapas.addItem(arcGISLocalFeatureLayer.getName()); 
							CmbCapas.setSelectedItem(arcGISLocalFeatureLayer.getName());
						}
						crearTree(arcGISLocalFeatureLayer); 
						break;
					}
					
				} else {
					if(CmbCapas.getSelectedItem().equals(arcGISLocalFeatureLayer.getName())){
						if (arcGISLocalFeatureLayer.getSelectionIDs().length >0) {
							crearTree(arcGISLocalFeatureLayer); 
						}
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
//					System.out.println("namelAYER............. "+arcGISLocalFeatureLayer.getName());
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
		        table.setSize(new Dimension(300, 500)); 
				table.setBorder(new LineBorder(new Color(30, 144, 255), 1, true));
				table.setBackground(Color.white);
				JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				scrollPane.getViewport().setBackground(Color.white); 
				String idFeature= mostrarInformacion(graphic, table); 
				tabbedPane.add(""+idFeature, scrollPane);
				//obtenerInformacion(arcGISLocalFeatureLayer, graphic.getUid(), table);
			}
		} catch (Exception e) { 
			System.err.println("crearTree e:  "+e);
		}
	}

	public String mostrarInformacion(Graphic graphic, final JTable table) {
		Object[][] datosFeature= new Object[][]{};
		Map<String, Object> listaAtributos = graphic.getAttributes();
		String idFeature ="0";
		if (listaAtributos != null) { 
			datosFeature= new Object[listaAtributos.size()][2];
			for (int i=0; i<listaAtributos.size(); i++) {
				String nombreCampo = (String) listaAtributos.keySet().toArray()[i]+"";
				String valorCampo = listaAtributos.values().toArray()[i]+""; 
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
				if (nombreCampo.toLowerCase().equals("objectid")) {
					idFeature = valorCampo;
				}
				datosFeature[i][0]= nombreCampo;
				datosFeature[i][1] =valorCampo;
				
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
		} else {
			mostrarMensaje(table); 
		}
		return idFeature;
	}
	
	public void obtenerInformacion (final ArcGISLocalFeatureLayer arcGISLocalFeatureLayer, final int id, final JTable table) {
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
				System.out.println(" featureResult.featureCount() " + featureResult.featureCount() + " url "+arcGISLocalFeatureLayer.getUrl() +" id "+ id);
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
	}
	
	
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
