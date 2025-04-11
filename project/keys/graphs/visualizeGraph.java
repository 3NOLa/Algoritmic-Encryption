package project.keys.graphs;
import java.util.*;

public class visualizeGraph {
    private final DirectedGraph graph;
    private final ArrayList<ArrayList<Vertex>> layers;
    private final LinkedList<Vertex> topologicalSort;

    public visualizeGraph(DirectedGraph graph) {
        this.graph = graph;
        this.layers = new ArrayList<>();
        this.topologicalSort = new TopologicalSort(this.graph).traceVertcies();
    }

    private ArrayList<Vertex> findRootNodes() {
        ArrayList<Vertex> roots = new ArrayList<>();
        
        // Mark nodes with incoming edges
        for (Vertex v : topologicalSort)
            if (v.getInDegree() == 0)
                roots.add(v);

        return roots;
    }

    public void assignLayersBfs(Vertex start, HashMap<Vertex, Integer> layerMap) {
        Queue<Vertex> queue = new LinkedList<>();
        queue.offer(start);
        layerMap.put(start, 0);

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            int currentLayer = layerMap.get(current);
            
            // Put all children in next layer down
            for (Vertex neighbor : current.getEdges()) {
                int neighborLayer = layerMap.getOrDefault(neighbor, -1);
                if (neighborLayer < currentLayer + 1) {
                    layerMap.put(neighbor, currentLayer + 1);
                    queue.offer(neighbor);
                }
            }
        }
    
    }

    public void setLayers() 
    {
        HashMap<Vertex, Integer> layerMap = new HashMap<>();
        ArrayList<Vertex> roots = findRootNodes();
        
        // Assign layers starting from each root
        for (Vertex root : roots) {
            assignLayersBfs(root, layerMap);
        }
        
        // Find how many layers we need
        int maxLayer = 0;
        for (int layer : layerMap.values()) {
            maxLayer = (layer > maxLayer) ? layer : maxLayer;
        }
        
        // Create the layers
        for (int i = 0; i <= maxLayer; i++) {
            layers.add(new ArrayList<>());
        }
        
        // Put vertices in their layers
        for (Vertex v : topologicalSort) {
            int layer = layerMap.getOrDefault(v, 0);
            layers.get(layer).add(v);
        }

    }

    public void optimizeLayout() {
        setLayers();
        minimizeCrossings();
    }

    // Layer by layer crossing minimization
    private void minimizeCrossings() {
        boolean improved;
        do {
            improved = false;
            for (int i = 1; i < layers.size(); i++) {
                improved |= minimizeLayerCrossings(layers.get(i), layers.get(i-1));//this will be true even if the minize got true only once because of the or(|)
            }
        } while (improved);
    }

    private boolean minimizeLayerCrossings(ArrayList<Vertex> upperLayer, ArrayList<Vertex> lowerLayer) {
        boolean improved = false;
        for (int i = 0; i < upperLayer.size() - 1; i++) {
            for (int j = i + 1; j < upperLayer.size(); j++) {
                if (shouldSwap(upperLayer.get(i), upperLayer.get(j), lowerLayer)) {
                    swap(upperLayer, i, j);
                    improved = true;
                }
            }
        }
        return improved;
    }

    private boolean shouldSwap(Vertex v1, Vertex v2, ArrayList<Vertex> lowerLayer) {
        double delta1 = calculateBarycenter(v1, lowerLayer);
        double delta2 = calculateBarycenter(v2, lowerLayer);
        return delta1 > delta2;
    }

    private double calculateBarycenter(Vertex vertex, ArrayList<Vertex> lowerLayer) {
        List<Vertex> edges = vertex.getEdges();
        if (edges.isEmpty()) return 0;

        double sum = 0;
        int count = 0;

        for (Vertex neighbor : edges) {
            int index = lowerLayer.indexOf(neighbor);
            if (index != -1) {
                sum += index;
                count++;
            }
        }

        return count > 0 ? sum / count : 0;
    }

    private void swap(ArrayList<Vertex> layer, int i, int j) {
        Vertex temp = layer.get(i);
        layer.set(i, layer.get(j));
        layer.set(j, temp);
    }

    public ArrayList<ArrayList<Vertex>> getLayers() {
        return layers;
    }

    public void printLayers() {
        for (int i = 0; i < layers.size(); i++) {
            System.out.printf("Layer %d: ", i);
            for (Vertex v : layers.get(i)) {
                System.out.printf("%s ", v.getValue());
            }
            System.out.println();
        }   
    }
}