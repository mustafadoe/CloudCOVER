import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
import java.util.ArrayList;

import javax.swing.JFrame;
import org.apache.commons.collections15.Transformer;

/* 
 * 
 * This file is part of Cloud-COVER, which is released under the GNU General Public License
 * See the License.txt file for full license details
 */


public class ViewDisplay {
	DirectedSparseGraph<String,String> g;
    public ViewDisplay() {
    	g=new DirectedSparseGraph<String,String>();
    }

    public void addall(){
    	ArrayList<VM> allvms = VM.allvms;
    	for (int i = 0; i < allvms.size(); i++){
    		VM vmtograph = allvms.get(i);
    		g.addVertex(vmtograph.name);
    	}
    	ArrayList<Connexion> allcons = Connexion.returncomms();
    	for (int i = 0; i < allcons.size(); i++){
    		Connexion contograph = allcons.get(i);
    		VM vm1 = contograph.vm1;
    		VM vm2 = contograph.vm2;    		
    		g.addEdge(contograph.name, vm1.name, vm2.name);
    	}
    }
    
    public void addvertex(String vm){
    	g.addVertex(vm);
    }
    public void addedge(String conname, String vm1name, String vm2name){
    	g.addEdge(conname,vm1name,vm2name);
    }
    
//    public static void main(String[] args) {
   	public void createmap(){
   		System.out.println("INCREATEMAp");
        ViewDisplay sgv = new ViewDisplay(); // This builds the graph
        sgv.addall();
        // Layout<V, E>, VisualizationComponent<V,E>
        Layout<String, String> layout = new CircleLayout(sgv.g);
        layout.setSize(new Dimension(600,600));
        BasicVisualizationServer<String,String> vv = new BasicVisualizationServer<String,String>(layout);
        vv.setPreferredSize(new Dimension(750,750));       
        // Setup up a new vertex to paint transformer...
        Transformer<String,Paint> vertexPaint = new Transformer<String,Paint>() {
            public Paint transform(String i) {
                return Color.GREEN;
            }
        };  
        // Set up a new stroke Transformer for the edges
        float dash[] = {10.0f};
        final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
             BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
        Transformer<String, Stroke> edgeStrokeTransformer = new Transformer<String, Stroke>() {
            public Stroke transform(String s) {
                return edgeStroke;
            }
        };
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);        
        
        JFrame frame = new JFrame("Simple Graph View 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);     
    }
    
}
