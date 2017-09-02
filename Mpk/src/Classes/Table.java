package Classes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.l2fprod.common.swing.LookAndFeelTweaks;

import Classes.Render;
import UpperEssential.UpperEssentialLookAndFeel;
import javafx.scene.control.Slider;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.BevelBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.FlowLayout;

public class Table extends JFrame {
	public Table() {
		JSlider jlJSlider = new JSlider();
		JButton jbButton = new JButton("ssss");
		jlJSlider.setName("slider");
		jbButton.setName("m");
		table = new JTable(); 
		table.setModel(new DefaultTableModel(
				new Object[][] {
					{true, "predio", jlJSlider, jbButton},
					{false, "predio", jlJSlider, jbButton},
					{false, "predio", jlJSlider, jbButton},
					{true, "predio",jlJSlider , jbButton},
				},
				new String[] {
						"chek", "nombre", "Jslider","button"
				}
				) {
			Class[] columnTypes = new Class[] {
					Boolean.class, 
					String.class,
					JButton.class,
					JButton.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			@Override
			public boolean isCellEditable(int row, int column) {
				// Sobrescribimos este método para evitar que la columna que contiene los botones sea editada.
				return !(this.getColumnClass(column).equals(JButton.class));
			}

		});

		table.setDefaultRenderer(JButton.class, new TableCellRenderer() {
			public Component getTableCellRendererComponent(JTable jtable, Object objeto, boolean estaSeleccionado, boolean tieneElFoco, int fila, int columna) {
				return (Component) objeto;
			}
		});

		/**
		 * Por último, agregamos un listener que nos permita saber cuando fue pulsada la celda que contiene el botón.
		 * Noten que estamos capturando el clic sobre JTable, no el clic sobre el JButton. Esto también implica que en 
		 * lugar de poner un botón en la celda, simplemente pudimos definir un CellRenderer que hiciera parecer que la 
		 * celda contiene un botón. Es posible capturar el clic del botón, pero a mi parecer el efecto es el mismo y 
		 * hacerlo de esta forma es más "simple"
		 */
		table.addMouseListener(new MouseAdapter() {
			Object value;
			public void mouseClicked(MouseEvent e) {
				//	                int fila = table.rowAtPoint(e.getPoint());
				//	                int columna = table.columnAtPoint(e.getPoint());
				//	                value = table.getValueAt(fila, columna);
				//	                System.out.println(" value "+ value); 
				//	                if(value instanceof JButton){
				//	                	System.out.println("Click en el boton modificar");
				//	                } else if (value instanceof JSlider) {
				//	                	 System.out.println("Click en el slider");
				//	                }
			}

			public void mousePressed(MouseEvent e) {
				int fila = table.rowAtPoint(e.getPoint());
				int columna = table.columnAtPoint(e.getPoint());
				value = table.getValueAt(fila, columna);
				System.out.println(" value "+ value); 
				if(value instanceof JButton){
					System.out.println("Click en el boton modificar");
				} else if (value instanceof JSlider) {
					System.out.println("Click en el slider");
				}
			}
			public void mouseReleased(MouseEvent arg0) {

			}
		});

		table.addMouseMotionListener(new MouseMotionListener() {

			public void mouseMoved(MouseEvent arg0) {

			}

			public void mouseDragged(MouseEvent arg0) {

			}
		});

		table.setRowSelectionAllowed(false);
		table.setSurrendersFocusOnKeystroke(true);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);

		getContentPane().add(table, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel.add(panel_1);

		JCheckBox checkBox = new JCheckBox("");
		panel_1.add(checkBox);

		JLabel lblNewLabel = new JLabel("New label");
		panel_1.add(lblNewLabel);

		JSlider slider = new JSlider();
		panel_1.add(slider);



	}

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//UIManager.setLookAndFeel(new UpperEssentialLookAndFeel());
					Table frame = new Table();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void ver_tabla(JTable tabla){

//		tabla.setDefaultRenderer(Object.class, new Render());

		JSlider slider = new JSlider();
		slider.setName("e"); 
		JButton btn1 = new JButton("Modificar");
		btn1.setName("m");
		btn1.setSize(new Dimension(50, 30));  
		slider.setSize(new Dimension(80, 30)); 
		JButton btn2 = new JButton("Eliminar");
		btn2.setName("e");

		DefaultTableModel d = new DefaultTableModel
				(
						new Object[][]{{"1","Pedro",btn1,slider},{"2","Juan",btn1,slider},{"3","Rosa",btn1,slider},{"4","Maria",btn1,slider}},
						new Object[]{"Codigo","Nombre","M","E"}
						)
		{
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};

		tabla.setModel(d);

		tabla.setPreferredScrollableViewportSize(tabla.getPreferredSize());


	}
}
