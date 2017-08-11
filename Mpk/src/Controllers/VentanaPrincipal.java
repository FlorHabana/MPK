package Controllers;

import AppPackage.AnimationClass;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.Instant;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.esri.map.JMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class VentanaPrincipal extends JFrame {
    static VentanaPrincipal frame = new VentanaPrincipal();
    CrearMapa crearMapa=new CrearMapa();
    static AnimationClass animationClass= new AnimationClass();
     JLabel lblImagen1 = new JLabel();
    
    private JMap map = new JMap();
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame.setVisible(true);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public VentanaPrincipal() {
        setBounds(100, 100, 1200, 700);
        setIconImage(Toolkit.getDefaultToolkit().getImage(MainApplication.class.getResource("/img/earth-globe.png")));
        Dimension d= getSize();
        PanelImagen panelImagen = new PanelImagen();
        getContentPane().add(panelImagen);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panelSlider= new JPanel();
        panelSlider.setPreferredSize(new Dimension((int) d.getWidth(), 100));
        getContentPane().add(panelSlider, BorderLayout.NORTH);
        
        
        lblImagen1.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/img/fondoWorld.png")));
        
        JLabel lblImagen2 = new JLabel("");
        lblImagen2.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/img/world2.png")));
        GroupLayout gl_panelSlider = new GroupLayout(panelSlider);
        gl_panelSlider.setHorizontalGroup(
        	gl_panelSlider.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panelSlider.createSequentialGroup()
        			.addComponent(lblImagen1)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(lblImagen2)
        			.addContainerGap(758, Short.MAX_VALUE))
        );
        gl_panelSlider.setVerticalGroup(
        	gl_panelSlider.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panelSlider.createSequentialGroup()
        			.addGroup(gl_panelSlider.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblImagen1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblImagen2))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelSlider.setLayout(gl_panelSlider);
        
        JPanel panelCapas = new JPanel();
        panelCapas.setPreferredSize(new Dimension(300, 660));
        panelCapas.setLayout(new BorderLayout(0, 0));
        crearMapa.dibujarCapas(map, panelCapas); 
        getContentPane().add(panelCapas, BorderLayout.WEST);

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.WHITE), BorderFactory.createEmptyBorder(5,50,5,50)));
        panelImagen.add(panel);
        
        Color color= new Color(0f, 0f, 0f, 0f);
        
        JButton btnDeleteData = new JButton("");
        btnDeleteData.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/img/garbage.png")));
        btnDeleteData.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(color, 1), BorderFactory.createLineBorder(color,2)));
        btnDeleteData.setBackground(color);
        panel.add(btnDeleteData);

        JButton btnIdentify = new JButton("");
        btnIdentify.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/img/info24.png")));
        btnIdentify.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(color, 1), BorderFactory.createLineBorder(color,2)));
        btnIdentify.setBackground(color);
        panel.add(btnIdentify);
        slideShow(lblImagen1, lblImagen2);
        
    }
    
    public static void slideShow(final JLabel lblImagen1, final JLabel lblImagen2){
    	System.out.println("Entre slideshow");
//    	lblImagen2.setVisible(false);
    	new Thread(){
    		int count;
    		@Override
    		public void run(){
    			try {
					while(true){
						switch (count) {
						case 0:
							Thread.sleep(20);
							animationClass.jLabelXLeft(-1200,0, 20, 1, lblImagen1);
							animationClass.jLabelXLeft(1200,0, 20, 1, lblImagen2);
							System.out.println("Caso 0");
							count=1;
							break;
						case 1:
							Thread.sleep(5);
							animationClass.jLabelXLeft(0, -1200, 20, 1, lblImagen1);
							animationClass.jLabelXLeft(0, 1200, 20, 1, lblImagen2);
							System.out.println("Caso 1");
							count=0;
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