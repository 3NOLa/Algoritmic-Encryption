package project.keys.graphs;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphVisualizer extends JPanel {
    private static final int NODE_RADIUS = 20;
    private static final int VERTICAL_SPACING = 75;
    private static final int HORIZONTAL_SPACING = 150;
    private final visualizeGraph layoutManager;
    private final HashMap<Vertex, Point2D> vertexPositions;
    private int height = 800;
    private int width = 600;

    public GraphVisualizer(visualizeGraph layoutManager) {
        this.layoutManager = layoutManager;
        this.vertexPositions = new HashMap<>();
        setPreferredSize(new Dimension(this.width,this.height));
        calculatePositions();
    }

    private void calculatePositions() {
        ArrayList<ArrayList<Vertex>> layers = layoutManager.getLayers();
        
        for (int layerIndex = 0; layerIndex < layers.size(); layerIndex++) {
            ArrayList<Vertex> layer = layers.get(layerIndex);
            double y = VERTICAL_SPACING * (layerIndex + 1);

            this.height = (this.height < y) ? (int) y : this.height; 
            
            for (int nodeIndex = 0; nodeIndex < layer.size(); nodeIndex++) {
                double x = HORIZONTAL_SPACING * (nodeIndex + 1);
                // Center nodes within their layer
                x += (getPreferredSize().width - (layer.size() + 1) * HORIZONTAL_SPACING) / 2;
                vertexPositions.put(layer.get(nodeIndex), new Point2D.Double(x, y));
            }
        }

        this.height += 50;
        setPreferredSize(new Dimension(this.width,this.height));
        revalidate();  // Recalculate layout
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw edges first
        g2d.setStroke(new BasicStroke(1.5f));
        for (ArrayList<Vertex> layer : layoutManager.getLayers()) {
            for (Vertex vertex : layer) {
                Point2D sourcePos = vertexPositions.get(vertex);
                for (Vertex neighbor : vertex.getEdges()) {
                    Point2D targetPos = vertexPositions.get(neighbor);
                    if (targetPos != null) {
                        drawEdge(g2d, sourcePos, targetPos);
                    }
                }
            }
        }

        // Draw vertices
        for (ArrayList<Vertex> layer : layoutManager.getLayers()) {
            for (Vertex vertex : layer) {
                Point2D pos = vertexPositions.get(vertex);
                drawVertex(g2d, pos, Integer.toString(vertex.getValue()));
            }
        }
    }

    private void drawEdge(Graphics2D g2d, Point2D source, Point2D target) {
        // Draw arrow
        g2d.setColor(Color.BLACK);
        double dx = target.getX() - source.getX();
        double dy = target.getY() - source.getY();
        double angle = Math.atan2(dy, dx);
        
        // Adjust start and end points to account for node radius
        double startX = source.getX() + NODE_RADIUS * Math.cos(angle);
        double startY = source.getY() + NODE_RADIUS * Math.sin(angle);
        double endX = target.getX() - NODE_RADIUS * Math.cos(angle);
        double endY = target.getY() - NODE_RADIUS * Math.sin(angle);
        
        g2d.draw(new Line2D.Double(startX, startY, endX, endY));
        
        // Draw arrowhead
        int arrowSize = 10;
        double arrowAngle = Math.PI / 6;
        double x1 = endX - arrowSize * Math.cos(angle - arrowAngle);
        double y1 = endY - arrowSize * Math.sin(angle - arrowAngle);
        double x2 = endX - arrowSize * Math.cos(angle + arrowAngle);
        double y2 = endY - arrowSize * Math.sin(angle + arrowAngle);
        
        g2d.draw(new Line2D.Double(endX, endY, x1, y1));
        g2d.draw(new Line2D.Double(endX, endY, x2, y2));
    }

    private void drawVertex(Graphics2D g2d, Point2D position, String label) {
        double x = position.getX() - NODE_RADIUS;
        double y = position.getY() - NODE_RADIUS;
        
        // Draw node circle
        g2d.setColor(Color.WHITE);
        g2d.fill(new Ellipse2D.Double(x, y, 2 * NODE_RADIUS, 2 * NODE_RADIUS));
        g2d.setColor(Color.BLACK);
        g2d.draw(new Ellipse2D.Double(x, y, 2 * NODE_RADIUS, 2 * NODE_RADIUS));
        
        // Draw label
        FontMetrics fm = g2d.getFontMetrics();
        int labelWidth = fm.stringWidth(label);
        int labelHeight = fm.getHeight();
        g2d.drawString(label, 
            (float)(position.getX() - labelWidth/2), 
            (float)(position.getY() + labelHeight/4));
    }

    public static void displayGraph(DirectedGraph graph) {
        visualizeGraph layout = new visualizeGraph(graph);
        layout.optimizeLayout();
        
        JFrame frame = new JFrame("Graph Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GraphVisualizer(layout));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}