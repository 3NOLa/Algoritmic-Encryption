package project.keys.graphs;
import java.util.*;

public class Vertex
{
    private int value;
    private int inDegree;
    private LinkedList<Vertex> edges;

    public Vertex(int value)
    {
        this.value = value;
        this.inDegree = 0;
        edges = new LinkedList<>();
    }

    public Vertex()
    {
        this.value = (int)(Math.random() * 255);
        this.inDegree = 0;
        edges = new LinkedList<>();
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return this.value;
    }

    public int getInDegree()
    {
        return this.inDegree;
    }

    public void addInDgree()
    {
        this.inDegree++;
    }

    public LinkedList<Vertex> getEdges()
    {
        return edges;
    }

    public void addEdge(Vertex e)
    {
        this.edges.addLast(e);
    }

    public void removeEdge()
    {
        this.edges.removeLast();
    }
}