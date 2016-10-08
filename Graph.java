import java.util.Random;
import java.util.Arrays;
import java.util.LinkedList;

class Graph
{
private int[][] adjMatrix;
private int n;
private Random rand;

public Graph(int n)
{
        this.n = n;
        rand = new Random();
        adjMatrix = new int[n][n];
        for(int i = 0; i < n; i++)
        {
                for(int j = 0; j < n; j++)
                {
                        adjMatrix[i][j] = 0;
                }
        }

        // Init
        adjMatrix[2][0] = 1;
        adjMatrix[2][1] = 1;
        adjMatrix[0][2] = 1;
        adjMatrix[1][2] = 1;
}

// Barabási–Albert model
public void generateScaleFree()
{
        double p;
        for(int i = 3; i < n; i++)
        {
                for(int j = 0; j < i; j++)
                {
                        p = vertexEdge(j)/totalEdge();
                        if(p > rand.nextDouble())
                        {
                                adjMatrix[i][j] = 1;
                                adjMatrix[j][i] = 1;
                        }
                }
        }
}

private double totalEdge()
{
        double total = 0;
        for(int i = 0; i < n; i++)
        {
                for(int j = 0; j < n; j++)
                {
                        if(adjMatrix[i][j] == 1)
                        {
                                total++;
                        }
                }
        }
        return total;
}

private double vertexEdge(int k)
{
        double total = 0;
        for(int i = 0; i < n; i++)
        {
                if(adjMatrix[k][i] == 1)
                {
                        total++;
                }
        }
        return total;
}

// From http://www.geeksforgeeks.org/bipartite-graph/
public boolean isBipartite()
{
        // Create a color array to store colors assigned to all veritces.
        // Vertex number is used as index in this array. The value '-1'
        // of  colorArr[i] is used to indicate that no color is assigned
        // to vertex 'i'.  The value 1 is used to indicate first color
        // is assigned and value 0 indicates second color is assigned.
        int colorArr[] = new int[n];
        for (int i = 0; i < n; ++i)
                colorArr[i] = -1;

        // Assign first color to source
        colorArr[0] = 1;

        // Create a queue (FIFO) of vertex numbers and enqueue
        // source vertex for BFS traversal
        LinkedList<Integer>q = new LinkedList<Integer>();
        q.add(0);

        // Run while there are vertices in queue (Similar to BFS)
        while (q.size() != 0)
        {

                // Dequeue a vertex from queue
                int u = q.poll();

                // Find all non-colored adjacent vertices
                for (int v = 0; v < n; ++v)
                {
                        // An edge from u to v exists and destination v is
                        // not colored
                        if (adjMatrix[u][v]==1 && colorArr[v]==-1)
                        {
                                // Assign alternate color to this adjacent v of u
                                colorArr[v] = 1-colorArr[u];
                                q.add(v);
                        }

                        // An edge from u to v exists and destination v is
                        // colored with same color as u
                        else if (adjMatrix[u][v]==1 && colorArr[v]==colorArr[u])
                                return false;
                }
        }
        // If we reach here, then all adjacent vertices can
        //  be colored with alternate color
        return true;
}


public static void main(String[] args)
{
        Graph g = new Graph(60);
        g.generateScaleFree();
        while(!g.isBipartite())
        {
                g = new Graph(60);
                g.generateScaleFree();
        }
}


}
