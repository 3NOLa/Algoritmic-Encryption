package project.keys.graphs;
import java.util.*;

abstract public class Graph {

    String name;
    int nodeCount;
    int averageDegree;
    ArrayList<Vertex> vertices;

    public Graph(String name,int nodeCount,int averageDegree)
    {
        this.name = name;
        this.nodeCount = nodeCount;
        this.averageDegree = averageDegree;
        vertices = new ArrayList<>();
    }

    public void generateGraph()
    {
        generateVertics();
        generateEdges();
    }

    public void generateVertics()
    {
        for(int i = 0;i<this.nodeCount;i++)
        {
            Vertex node = new Vertex();
            this.vertices.add(node);
        }

    }  

    abstract public void generateEdges();

    abstract public boolean checkCycle(Vertex start, Vertex end);

    abstract public boolean cycleDfs(Vertex v,Vertex parent,Vertex end,ArrayList<Vertex> visited);

    public void addVertex(int value)
    {
        Vertex v = new Vertex(value);
        
        this.vertices.add(v);
    }

    public void removeDuplicates(Vertex v)
    {
        HashSet<Vertex> dups = new HashSet<>();

        Iterator<Vertex> itr = v.getEdges().iterator();

        while(itr.hasNext())
        {
            Vertex e = itr.next();
            if(dups.contains(e))
                itr.remove();
            else
                dups.add(e);
        }
    }

    @Override
    public String toString()
    {
        StringBuilder string = new StringBuilder("Graph name: " + this.name + "\nNode count: " + this.nodeCount + "\naverage Degree per node " + this.averageDegree + "\nVertic and edges:\n");

        for(int i=0;i<this.nodeCount;i++)
        {
            Vertex v = this.vertices.get(i);
            string.append(Integer.toString(v.getValue()) + ": {");

            for(Vertex edge : v.getEdges())
            {
                string.append(Integer.toString(edge.getValue()) + ", ");
            }

            string.delete(string.length()-2, string.length());
            string.append("}\n");

        }

        return  string.toString();
    }

}

class UndirectedGraph extends Graph{

    public UndirectedGraph(String name,int nodeCount,int averageDegree)
    {
        super(name, nodeCount, averageDegree);
    }

    public void addEdge(Vertex a, Vertex b)
    {
        a.addEdge(b);
        b.addEdge(a);
    }

    @Override
    public void generateEdges()
    {
        for(int i=0;i<this.nodeCount;i++)
        {
            int j=this.averageDegree;
            int loops=0;

            Vertex v = this.vertices.get(i);

            while (j>0 && loops < this.nodeCount) {

                
                int randomIndex = (int)(Math.random() * this.nodeCount);
                Vertex randomVertic = this.vertices.get(randomIndex);

                
                if (randomVertic != v && !checkCycle(randomVertic, v)) {
                    v.addEdge(randomVertic);
                    v.addInDgree();
                    randomVertic.addEdge(v);
                    randomVertic.addInDgree();
                    j--;
                }
                loops++;
            }

            super.removeDuplicates(v);
        }
    }

    @Override
    public boolean cycleDfs(Vertex v,Vertex parent,Vertex end,ArrayList<Vertex> visited)
    {
        visited.add(v);

        for(Vertex e : v.getEdges())
        {

            if(e == end && end != parent)
            {
                return true;
            }
            else if(!visited.contains(e))
            {
                if (cycleDfs(e, v , end, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkCycle(Vertex start, Vertex end)
    {
        ArrayList<Vertex> visited = new ArrayList<>();
        return cycleDfs(start,end ,end, visited);
    }
}

class main2
{
    public static void main(String[] args) {
        Graph gun = new UndirectedGraph("undirected graph", 17, 3);
        gun.generateGraph();
        System.out.println(gun.toString());

        Graph g = new DirectedGraph("directed graph", 15, 3);
        g.generateGraph(); 
        System.out.println(g.toString());
        TopologicalSort sort = new TopologicalSort(g);
        LinkedList<Vertex> TopoSort = sort.traceVertcies();
        
        for(Vertex v : TopoSort)
        {
            System.out.print(v.getValue() + " ");
        }
        System.out.println();

        visualizeGraph viz = new visualizeGraph((DirectedGraph)g);
        viz.optimizeLayout();
        viz.printLayers();

        GraphVisualizer.displayGraph((DirectedGraph)g);
    }
}
