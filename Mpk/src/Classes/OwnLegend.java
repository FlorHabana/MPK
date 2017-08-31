package Classes;

import com.esri.core.geometry.Geometry;
import com.esri.core.map.LayerLegendInfo;
import com.esri.core.map.LegendItemInfo;
import com.esri.map.ArcGISFeatureLayer;
import com.esri.map.GroupLayer;
import com.esri.map.Layer;
import com.esri.map.LayerInfo;
import com.esri.map.WmsLayerInfo;
import com.esri.toolkit.utilities.ExceptionHandler;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import org.apache.james.mime4j.codec.Base64InputStream;

public class OwnLegend extends JPanel implements TreeCellRenderer{
	private static final long serialVersionUID = 1L;
	private JCheckBox _visibilityCheckBox;
	private JLabel _nodeLabel;
	private JSlider _slider;
	private Hashtable<Integer, JLabel> opacitylabelTable = new Hashtable<Integer, JLabel>();

	public OwnLegend(){
		setLayout(new BoxLayout(this, 0));

		this._visibilityCheckBox = new JCheckBox("");
		add(this._visibilityCheckBox);

		this._nodeLabel = new JLabel("New label");
		add(this._nodeLabel);

		this._slider = new JSlider();
		add(this._slider);
	}
	
	public JSlider getSlider(){return this._slider;}

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, 
			boolean leaf, int row, boolean hasFocus){
		setBackground(selected ? SystemColor.textHighlight : tree.getBackground());
		this._nodeLabel.setForeground(selected ? SystemColor.textHighlightText : SystemColor.textText);
		this._visibilityCheckBox.setBackground(new Color(0,0,0,0));
		this._visibilityCheckBox.setVisible(true);
		
		DefaultMutableTreeNode nodeToRender = (DefaultMutableTreeNode)value;
		final Object userObject = nodeToRender.getUserObject();
		
		if ((userObject instanceof Layer)) {
			renderLayer((Layer)userObject);
		} else if ((userObject instanceof LayerInfo)) {
			renderSubLayer((LayerInfo)userObject, nodeToRender);
		} else if ((userObject instanceof LayerLegendInfo)) {
			renderLegendInfo((LayerLegendInfo)userObject);
		} else if ((userObject instanceof LegendItemInfo)) {
			renderLegendItem((LegendItemInfo)userObject);
		}
		
		return this;
	}

	private void renderLegendInfo(LayerLegendInfo legendInfo){
		this._visibilityCheckBox.setVisible(false);
		this._nodeLabel.setText(legendInfo.getLayerName());
		this._nodeLabel.setIcon(new ImageIcon(getClass().getResource("/com/esri/client/toolkit/images/LayerServiceMap16.png")));
		this._slider.setVisible(false);
	}

	private void renderLegendItem(LegendItemInfo legendItem){
		ImageIcon imageIcon = null;
		this._visibilityCheckBox.setVisible(false);
		this._slider.setVisible(false);
		this._nodeLabel.setText(legendItem.getLabel());
		byte[] imageBytes = legendItem.getImageBytes();
		if (imageBytes != null){
			ByteArrayInputStream bin = new ByteArrayInputStream(imageBytes);
			Base64InputStream b64in = new Base64InputStream(bin);
			try{
				BufferedImage decodedImage = ImageIO.read(b64in);
				if (decodedImage != null) {
					imageIcon = new ImageIcon(decodedImage);
				}
			}catch (IOException e) {}
			try{
				b64in.close();
			}catch (IOException e){
				ExceptionHandler.handleException(this, e);
			}
		}
		this._nodeLabel.setIcon(imageIcon);
	}

	private void renderSubLayer(LayerInfo layerInfo, DefaultMutableTreeNode nodeToRender){
		DefaultMutableTreeNode firstChild = null;
		ImageIcon icon = null;
		if (nodeToRender.getChildCount() > 0) {
			firstChild = (DefaultMutableTreeNode)nodeToRender.getFirstChild();
		}
		if ((firstChild != null) && ((firstChild.getUserObject() instanceof LayerInfo))) {
			icon = new ImageIcon(getClass().getResource("/com/esri/client/toolkit/images/LayerGroup16.png"));
		} else {
			icon = new ImageIcon(getClass().getResource("/com/esri/client/toolkit/images/LayerServiceMap16.png"));
		}
		this._visibilityCheckBox.setSelected(layerInfo.isVisible());
		this._slider.setEnabled(layerInfo.isVisible());
		if ((layerInfo instanceof WmsLayerInfo)) {
			this._nodeLabel.setText(((WmsLayerInfo)layerInfo).getDisplayName());
		} else {
			this._nodeLabel.setText(layerInfo.getName());
		}
		this._nodeLabel.setIcon(icon);
	}

	@SuppressWarnings("incomplete-switch")
	private void renderLayer(final Layer layer){
		if (layer != null){
			this._visibilityCheckBox.setSelected(layer.isVisible());
//			customizeComponents(layer);
			this._nodeLabel.setText(layer.getName());
			ImageIcon icon = null;
			if ((layer instanceof ArcGISFeatureLayer)){
				Geometry.Type geometryType = ((ArcGISFeatureLayer)layer).getGeometryType();
				customizeComponents(layer);
				if (geometryType != null) {
					System.out.println("geometryType~~~~~~~~~~~"+geometryType);
//					customizeComponents(layer);
					switch (geometryType){
					case POINT: 
					case MULTIPOINT: 
						icon = new ImageIcon(getClass().getResource("/com/esri/client/toolkit/images/LayerPoint16.png"));
						break;
					case LINE: 
					case POLYLINE: 
						icon = new ImageIcon(getClass().getResource("/com/esri/client/toolkit/images/LayerLine16.png"));
						break;
					case POLYGON: 
						icon = new ImageIcon(getClass().getResource("/com/esri/client/toolkit/images/LayerPolygon16.png"));
						break;
					}
				} else {
					icon = new ImageIcon(getClass().getResource("/com/esri/client/toolkit/images/LayerGeneric16.png"));
				}
			}
			else if ((layer instanceof GroupLayer)){
				icon = new ImageIcon(getClass().getResource("/com/esri/client/toolkit/images/LayerGroup16.png"));
			}else{
				icon = new ImageIcon(getClass().getResource("/com/esri/client/toolkit/images/LayerServiceMap16.png"));
			}
			this._nodeLabel.setIcon(icon);
		}
//		this.repaint();
//		this.updateUI();
	}
	
	/*********************JSlider Function****************************/
	private void customizeComponents(Layer layer){
		System.out.println("Customize JSlider");
		this._slider.setEnabled(layer.isVisible());
		this._slider.setValue((int)(layer.getOpacity()*100));
		this._slider.setVisible(true);
		this._slider.setBackground(new Color(0,0,0,0));
		valuesToSlider(opacitylabelTable);
		this._slider.setLabelTable(opacitylabelTable);
		this._slider.setMajorTickSpacing(50);
		this._slider.setPaintTicks(true);
		this._slider.setPaintLabels(true);
		this._slider.setSnapToTicks(true);
		this._slider.setFont(new Font("Serif", Font.ITALIC, 8));
		this._slider.addChangeListener(opacityListener(layer));
	}
	
	private void valuesToSlider(Hashtable<Integer, JLabel> table) {
		table.put(new Integer(100), new JLabel("1"));   
		table.put(new Integer(50), new JLabel("0.5"));   
		table.put(new Integer(0), new JLabel("0"));  
	}

	public ChangeListener opacityListener(final Layer layer){
		ChangeListener sliderListener= new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				System.out.println("Changement;;;;;;;;;;;;;;;;");
				JSlider source=(JSlider) e.getSource();
				layer.setOpacity((float)(source.getValue()/100));
			}
		};
		return sliderListener;
	}
}
