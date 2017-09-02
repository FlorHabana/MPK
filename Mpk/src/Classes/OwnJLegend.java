package Classes;

import com.esri.client.local.ArcGISLocalTiledLayer;
import com.esri.core.map.LayerLegendInfo;
import com.esri.core.map.LayerLegendInfoCollection;
import com.esri.core.map.LegendItemInfo;
import com.esri.map.ArcGISDynamicMapServiceLayer;
import com.esri.map.ArcGISFeatureLayer;
import com.esri.map.ArcGISTiledMapServiceLayer;
import com.esri.map.FeatureLayer;
import com.esri.map.GroupLayer;
import com.esri.map.JMap;
import com.esri.map.Layer;
import com.esri.map.LayerEvent;
import com.esri.map.LayerInfo;
import com.esri.map.LayerInitializeCompleteEvent;
import com.esri.map.LayerInitializeCompleteListener;
import com.esri.map.LayerListEventListenerAdapter;
import com.esri.map.WmsDynamicMapServiceLayer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class OwnJLegend extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTree _tree;
	private JMap _map;
	private DefaultMutableTreeNode _rootNode;

	protected class LegendMouseListener extends MouseAdapter{
		int _hotspot = new JCheckBox().getPreferredSize().width;

		protected LegendMouseListener() {}

		public void mouseClicked(MouseEvent e){

			if (OwnJLegend.this.isEnabled()){
				TreePath path = OwnJLegend.this._tree.getPathForLocation(e.getX(), e.getY());
				if ((path != null) && (e.getX() < OwnJLegend.this._tree.getPathBounds(path).x 
						+ this._hotspot)){
					Object userObject = ((DefaultMutableTreeNode)path.getLastPathComponent()).getUserObject();
					System.out.println("5555555555");
					if ((userObject instanceof Layer)){
						Layer layer = (Layer)userObject;
						JSliderEditorP editor= new JSliderEditorP();
						System.out.println("Agregando CellEditor");
						OwnJLegend.this._tree.setCellEditor(editor);
						layer.setVisible(!layer.isVisible());
					}else if ((userObject instanceof LayerInfo)){
						LayerInfo info = (LayerInfo)userObject;
						info.setVisible(!info.isVisible());
					}
					OwnJLegend.this._tree.repaint();
				}
			}
		}
	}

	protected class LegendTreeWillExpandListener implements TreeWillExpandListener{
		protected LegendTreeWillExpandListener() {}

		public void treeWillExpand(TreeExpansionEvent event)throws ExpandVetoException{
			if (OwnJLegend.this.isEnabled()) {
				OwnJLegend.this.handleExpandingNode(event);
			} else {
				throw new ExpandVetoException(event);
			}
		}

		public void treeWillCollapse(TreeExpansionEvent event) {}
	}

	protected class LegendLayerListEventListener extends LayerListEventListenerAdapter{
		protected LegendLayerListEventListener() {}

		public void layerAdded(LayerEvent event){
			OwnJLegend.this.addLayerToLegend(event.getChangedLayer(), OwnJLegend.this._rootNode, event.getLayerIndex());

			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					OwnJLegend.this._tree.expandPath(new TreePath(OwnJLegend.this._rootNode));
					OwnJLegend.this._tree.validate();
					OwnJLegend.this._tree.repaint();
				}
			});
		}

		public void multipleLayersAdded(LayerEvent event){
			for (Map.Entry<Integer, Layer> curEntry : event.getChangedLayers().entrySet()) {
				OwnJLegend.this.addLayerToLegend((Layer)curEntry.getValue(), 
						OwnJLegend.this._rootNode, ((Integer)curEntry.getKey()).intValue());
			}
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					OwnJLegend.this._tree.expandPath(new TreePath(OwnJLegend.this._rootNode));
					OwnJLegend.this._tree.validate();
					OwnJLegend.this._tree.repaint();
				}
			});
		}

		public void layerRemoved(LayerEvent event){
			OwnJLegend.this.removeLayerFromLegend(event.getChangedLayer());
		}

		public void multipleLayersRemoved(LayerEvent event){
			for (Map.Entry<Integer, Layer> curEntry : event.getChangedLayers().entrySet()) {
				OwnJLegend.this.removeLayerFromLegend((Layer)curEntry.getValue());
			}
		}
	}

	private LegendLayerListEventListener _layerListEventListener = new LegendLayerListEventListener();
	private LegendTreeWillExpandListener _treeWillExpandListener = new LegendTreeWillExpandListener();
	private DefaultTreeModel _model;
	private LegendMouseListener _legendMouseListener = new LegendMouseListener();

	public OwnJLegend(JMap map){
		this._map = map;
		setLayout(new BoxLayout(this, 0));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);

		this._tree = new JTree();
		scrollPane.setViewportView(this._tree);

		initTreeFromMap();
	}

	private void initTreeFromMap(){
		this._rootNode = new DefaultMutableTreeNode(null);
		this._model = new DefaultTreeModel(this._rootNode);
		this._tree.setRootVisible(false);
		this._tree.setShowsRootHandles(true);
		this._tree.setModel(this._model);
		if (this._map != null){
//			JSliderEditorP editor = new JSliderEditorP();
//			TreeCellEditor _editor= new DefaultTreeCellEditor(this._tree, new OwnLegend(), editor);
			this._tree.setCellRenderer(new OwnLegend());
			for (Layer curLayer : this._map.getLayers()) {
				addLayerToLegend(curLayer, this._rootNode, -1);	
			}
			this._tree.expandPath(new TreePath(this._rootNode));
			this._map.getLayers().addLayerListEventListener(this._layerListEventListener);
			this._tree.addTreeWillExpandListener(this._treeWillExpandListener);
			this._tree.addMouseListener(this._legendMouseListener);
		}else{
			throw new RuntimeException("JMap is null: the legend cannot be created");
		}
	}

	protected void addLayerToLegend(Layer layerToAdd, DefaultMutableTreeNode parentNode, int addAtIndex){
		int layerIndex = addAtIndex == -1 ? 0 : parentNode.getChildCount() - 1 - addAtIndex;
		layerIndex = Math.max(0, layerIndex);
		DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(layerToAdd, true);
		this._model.insertNodeInto(newChild, parentNode, layerIndex);

		this._model.insertNodeInto(new DefaultMutableTreeNode(), newChild, 0);

		layerToAdd.addLayerInitializeCompleteListener(new LayerInitializeCompleteListener(){
			public void layerInitializeComplete(LayerInitializeCompleteEvent e){
				SwingUtilities.invokeLater(new Runnable(){
					public void run(){
						OwnJLegend.this._model.reload();
					}
				});
			}
		});
	}

	protected void removeLayerFromLegend(Layer layerToRemove){
		for (int count = 0; count < this._rootNode.getChildCount(); count++){
			DefaultMutableTreeNode curNode = (DefaultMutableTreeNode)this._rootNode.getChildAt(count);
			if (curNode.getUserObject().equals(layerToRemove)){
				this._model.removeNodeFromParent(curNode);
				break;
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void handleExpandingNode(TreeExpansionEvent event)throws ExpandVetoException{
		TreePath path = event.getPath();

		DefaultMutableTreeNode expandingNode = (DefaultMutableTreeNode)path.getLastPathComponent();
		if (expandingNode != null){
			Object userObject = expandingNode.getUserObject();
			if ((userObject != null) && ((userObject instanceof Layer)) && 
					(((Layer)userObject).getStatus() != Layer.LayerStatus.INITIALIZED)) {
				throw new ExpandVetoException(event);
			}
			if (!expandingNode.isLeaf()){
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)expandingNode.getChildAt(0);
				Object firstChildUserObject = node.getUserObject();
				if (firstChildUserObject == null){
					expandingNode.remove(0);
					if ((userObject instanceof LayerInfo)){
						LayerInfo curLayerInfo = (LayerInfo)userObject;
						addLayerInfoToLegend(expandingNode, new ArrayList(curLayerInfo.getSubLayerInfos().values()));
					}else if ((userObject instanceof ArcGISDynamicMapServiceLayer)){
						ArcGISDynamicMapServiceLayer dynamicLayer = (ArcGISDynamicMapServiceLayer)userObject;
						addLayerInfoToLegend(expandingNode, new ArrayList(dynamicLayer.getLayers().values()));
					}else if ((userObject instanceof WmsDynamicMapServiceLayer)){
						WmsDynamicMapServiceLayer wmsLayer = (WmsDynamicMapServiceLayer)userObject;
						ArrayList<LayerInfo> layers = new ArrayList();
						for (LayerInfo curInfo : wmsLayer.getLayers()) {
							layers.add(curInfo);
						}
						addLayerInfoToLegend(expandingNode, layers);
					}else if ((userObject instanceof ArcGISTiledMapServiceLayer)){
						ArcGISTiledMapServiceLayer tiledLayer = (ArcGISTiledMapServiceLayer)userObject;
						addLayerLegendInfoToLegend(expandingNode, tiledLayer.getLegend());
					}else if ((userObject instanceof ArcGISLocalTiledLayer)){
						ArcGISLocalTiledLayer tiledLayer = (ArcGISLocalTiledLayer)userObject;
						addLayerLegendInfoToLegend(expandingNode, tiledLayer.getLegend());
					}else if ((userObject instanceof ArcGISFeatureLayer)){
						ArcGISFeatureLayer featureLayer = (ArcGISFeatureLayer)userObject;

						boolean allowExpand = false;
						LayerLegendInfoCollection legendInfos = featureLayer.getLegend();
						if (legendInfos != null){
							LayerLegendInfo legendInfo = legendInfos.getLayerLegendInfo(-1);
							if (legendInfo != null){
								allowExpand = true;
								for (LegendItemInfo curInfo : legendInfo.getLegendItemInfos()) {
									this._model.insertNodeInto(new DefaultMutableTreeNode(curInfo), 
											expandingNode, expandingNode.getChildCount());
								}
							}
						}
						if (!allowExpand){
							expandingNode.add(new DefaultMutableTreeNode());
							throw new ExpandVetoException(event);
						}
					}else if ((userObject instanceof FeatureLayer)){
						FeatureLayer featureLayer = (FeatureLayer)userObject;

						boolean allowExpand = false;
						LayerLegendInfoCollection legendInfos = featureLayer.getLegend();
						if (legendInfos != null){
							LayerLegendInfo legendInfo = legendInfos.getLayerLegendInfo(-1);
							if (legendInfo != null){
								allowExpand = true;
								for (LegendItemInfo curInfo : legendInfo.getLegendItemInfos()) {
									this._model.insertNodeInto(new DefaultMutableTreeNode(curInfo), 
											expandingNode, expandingNode.getChildCount());
								}
							}
						}
						if (!allowExpand){
							expandingNode.add(new DefaultMutableTreeNode());
							throw new ExpandVetoException(event);
						}
					}else if ((userObject instanceof GroupLayer)){
						for (Layer curLayer : ((GroupLayer)userObject).getLayers()) {
							addLayerToLegend(curLayer, expandingNode, -1);
						}
					}
				}else if (((firstChildUserObject instanceof LayerLegendInfo)) && (node.getChildCount() == 0)){
					expandingNode.remove(0);
					LayerLegendInfo legendInfo = (LayerLegendInfo)firstChildUserObject;
					for (LegendItemInfo curItem : legendInfo.getLegendItemInfos()) {
						this._model.insertNodeInto(new DefaultMutableTreeNode(curItem), 
								expandingNode, expandingNode.getChildCount());
					}
				}
			}
			this._tree.repaint();
		}
	}

	private void addLayerLegendInfoToLegend(DefaultMutableTreeNode expandingNode, LayerLegendInfoCollection legend){
		if (legend != null) {
			for (LayerLegendInfo curInfo : legend.getLayerLegendInfos()){
				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(curInfo);
				this._model.insertNodeInto(newNode, expandingNode, expandingNode.getChildCount());
				if (!curInfo.getLegendItemInfos().isEmpty()){
					DefaultMutableTreeNode dummyNode = new DefaultMutableTreeNode(curInfo);
					this._model.insertNodeInto(dummyNode, newNode, 0);
				}
			}
		}
	}

	protected void addLayerInfoToLegend(DefaultMutableTreeNode parentNode, List<LayerInfo> layerInfo){
		for (LayerInfo curLayerInfo : layerInfo){
			DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(curLayerInfo);
			this._model.insertNodeInto(newChild, parentNode, parentNode.getChildCount());
			if (curLayerInfo.getSubLayerInfos().size() > 0){
				this._model.insertNodeInto(new DefaultMutableTreeNode(), newChild, 0);
			}else{
				TreeNode[] path = parentNode.getPath();
				if (path.length >= 2){
					DefaultMutableTreeNode layerNode = (DefaultMutableTreeNode)path[1];

					Object userObject = layerNode.getUserObject();
					if ((userObject instanceof Layer)){
						LayerLegendInfoCollection legend = ((Layer)userObject).getLegend();
						LayerLegendInfo legendInfo = legend.getLayerLegendInfo(curLayerInfo.getId());
						if (legendInfo != null) {
							this._model.insertNodeInto(new DefaultMutableTreeNode(legendInfo), newChild, 0);
						}
					}
				}
			}
		}
	}
}