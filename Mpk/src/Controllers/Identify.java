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

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
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

public class Identify extends JFrame {
	MostrarCapas mostrarCapas = new MostrarCapas();
	
	private JPanel contentPane;
	@SuppressWarnings("rawtypes")
	JComboBox CmbCapas = new JComboBox();
	JPanel panel = new JPanel();
	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private final JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
	private final JTable table = new JTable();
	private final JPanel panel_1 = new JPanel();
	private final JPanel panel_2 = new JPanel();
	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "serial" })
	public Identify(final JMap map) {
		setResizable(false);
		setBounds(100, 100, 548, 548);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		
		JLabel lblIdentify = new JLabel("Identificar de : ");
		
		CmbCapas.setBackground(Color.WHITE);
		CmbCapas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obtenerFeatureSeleccionados(map);
			}
		});
		for(String name : mostrarCapas.llenarCombo(map)){
			CmbCapas.addItem(name);
		}
		
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane_2.setBackground(Color.WHITE);
		
		tabbedPane.addTab("New tab", null, tabbedPane_2, null);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{"id", "123"},
			},
			new String[] {
				"Campo", "Dato"
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class
			};
			@SuppressWarnings("rawtypes")
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setBorder(new LineBorder(new Color(30, 144, 255), 1, true));
		
		tabbedPane_2.addTab("New tab", null, table, null);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
					.addGap(10))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 311, GroupLayout.PREFERRED_SIZE)
					.addGap(3))
		);
		panel.setBackground(Color.WHITE);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
						.addComponent(panel_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(5)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
		);
		panel_1.setBackground(Color.WHITE);
		panel_2.setBackground(Color.WHITE);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(143)
					.addComponent(lblIdentify)
					.addGap(18)
					.addComponent(CmbCapas, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(170, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(CmbCapas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblIdentify)))
		);
		panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(30, 144, 255), new Color(100, 149, 237), new Color(0, 0, 205), new Color(30, 144, 255)));
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
		itemSeleccionado(CmbCapas, map);
	}

	public void obtenerFeatureSeleccionados(final JMap map){
		mostrarCapas.cleanPanel(panel_2);
		for (int i = 0; i < map.getLayers().size(); i++) {
			GroupLayer groupLayer = (GroupLayer)  map.getLayers().get(i);
			Layer[] layer= groupLayer.getLayers();
			for (int j = 0; j < layer.length; j++) {
				ArcGISLocalFeatureLayer arcGISLocalFeatureLayer = (ArcGISLocalFeatureLayer) layer[j];
				if(CmbCapas.getSelectedItem().equals(arcGISLocalFeatureLayer.getName())){
					crearTree(arcGISLocalFeatureLayer); 
				}
			}
		}
	}
	
	public void itemSeleccionado (JComboBox jcComboBox, JMap map) {
		int contador =0;
		if (jcComboBox.getSelectedItem() != null) {
			for (int i = 0; i < map.getLayers().size(); i++) {
				GroupLayer groupLayer = (GroupLayer)  map.getLayers().get(i);
				Layer[] layer= groupLayer.getLayers();
				for (int j = 0; j < layer.length; j++) {
					ArcGISLocalFeatureLayer arcGISLocalFeatureLayer = (ArcGISLocalFeatureLayer) layer[j];
					if (contador ==0) {
						if (arcGISLocalFeatureLayer.getSelectionIDs().length >0) {
							System.out.println("namelAYER............. "+arcGISLocalFeatureLayer.getName()); 
							CmbCapas.setSelectedItem(arcGISLocalFeatureLayer.getName()); 
							contador =1;
						}
					}
				}
			}
		}
	}
	
	public void crearTree (ArcGISLocalFeatureLayer arcGISLocalFeatureLayer) {
		mostrarCapas.cleanPanel(panel_2);
		DefaultMutableTreeNode padre = new DefaultMutableTreeNode(arcGISLocalFeatureLayer.getName());
		Graphic[] graphics =arcGISLocalFeatureLayer.getSelectedFeatures();
		for (int x=0; x<graphics.length; x++) {
			Graphic graphic = graphics[x];
			DefaultMutableTreeNode capa = new DefaultMutableTreeNode(graphic.getUid());
			padre.add(capa);
			//obtenerInformacion(arcGISLocalFeatureLayer, graphic.getUid());
		}
		DefaultTreeModel modelo = new DefaultTreeModel(padre);
		JTree jTree = new JTree(modelo);
		JScrollPane sp = new JScrollPane(jTree);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
		sp.updateUI();
		sp.setPreferredSize(new Dimension(475, 125));
		sp.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(30, 144, 255), new Color(100, 149, 237), new Color(0, 0, 205), new Color(30, 144, 255)));
		System.out.println("panel_2..........  "+panel_2.getWidth() + " heigt "+ panel_2.getHeight());
		panel_2.add(BorderLayout.NORTH, sp); 
		panel_2.updateUI();
	}
	
	public void obtenerInformacion (ArcGISLocalFeatureLayer arcGISLocalFeatureLayer, final int id) {
		Field[] fields = arcGISLocalFeatureLayer.getFields();
		String Campo="OBJECTID";
		for (int x=0; x<fields.length; x++) {  
			Field fiel = fields[x];
			if (x==0) {
				Campo =fiel.getName();
				System.out.println(" x "+x +" "+ fiel.getName() + " id "+id +" url "+arcGISLocalFeatureLayer.getUrl());
			}
		}
		
		QueryTask queryTask = new QueryTask(arcGISLocalFeatureLayer.getUrl());
		QueryParameters query = new QueryParameters();
		query.setReturnGeometry(true);
		query.setOutFields(new String[] {"*"});
		query.setWhere(Campo+"="+id  );
		System.out.println(" query " + query.getWhere());
		queryTask.execute(query, new CallbackListener<FeatureResult>() {
			public void onCallback(FeatureResult featureResult) {
				if (featureResult.featureCount() < 1) {
					System.err.println("Problem! There are no records returned");
					return;
				}
				mostrarInformacion(featureResult, id);
				
			}
			public void onError(Throwable e) {
				System.out.println(" e " + e);
			}
			
		});
	}
	
	public void mostrarInformacion (FeatureResult featureResult, int id) {
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBackground(Color.WHITE);
		tabbedPane.addTab(""+id, null, tabbedPane_1, null);
		JTable jaTable = new JTable();
		for (Object record : featureResult) {
			Feature feature = (Feature) record; 
			Polygon polygon = (Polygon) feature.getGeometry();
			System.out.println(" geometry  " + feature.getGeometry()+ " size "+polygon.getPointCount() +  " pat " +polygon.getPathCount() );
			for (int i=0; i<polygon.getPointCount(); i++) {
				System.out.println(" x " +polygon.getPoint(i).getX() +" y "+ polygon.getPoint(i).getY() ); 
			}
			Map<String, Object> hasResultado = feature.getAttributes();
			for (int i = 0; i < hasResultado.size(); i++) {
				System.out.println("nameCampo: " + hasResultado.keySet().toArray()[i] +" dato: "+ hasResultado.values().toArray()[i]);
			}
		}
	}
}
