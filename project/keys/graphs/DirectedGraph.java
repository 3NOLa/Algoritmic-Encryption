public class DirectedGraph extends Graph{

    public DirectedGraph(String name,int nodeCount,int averageDegree)
    {
        super(name, nodeCount, averageDegree);
    }

    public void addEdge(Vertex from, Vertex to)
    {
        from.addEdge(to);
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
            if(e == end)
                return true;

            else if(!visited.contains(e))
                if (cycleDfs(e, v , end, visited)) {
                    return true;
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