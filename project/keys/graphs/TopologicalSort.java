package project.keys.graphs;
import java.util.HashSet;
import java.util.LinkedList;

public class TopologicalSort {
    
    Graph graph;
    HashSet<Vertex> visited;
    LinkedList<Vertex> sorted;


    public TopologicalSort(Graph graph)
    {
        this.graph = graph;
        this.visited = new HashSet<>();
        this.sorted = new LinkedList<>();
    }

    public LinkedList<Vertex> traceVertcies()
    {
        for(Vertex v : this.graph.vertices)
        {
            if (!visited.contains(v)) {
                sortDfs(v);
            }
            
        }
        return sorted;
    }

    public void sortDfs(Vertex v)
    {
        visited.add(v);
        
        for(Vertex e : v.getEdges())
        {
            if (!visited.contains(e)) {
                sortDfs(e);
            }
        }
        
        sorted.addFirst(v);
    }
}
