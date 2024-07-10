// TITLE: PathFinder.java
// AUTHOR: IHSAN ARDA CAGLAYAN

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Stack;

import java.util.ArrayList;
import java.util.List;

// PATHFINDER CLASS IS ESSENTIALY DijkstraUndirectedSP IN A HEAVILY MODIFIED WAY.
public class PathFinder {
    private double[] distTo;          // distTo[v] = distance  of shortest s->v path
    private Edge[] edgeTo;            // edgeTo[v] = last edge on shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices
    private int sizeX;
    private int sizeY;
    private EdgeWeightedGraph inputGraph;


    public PathFinder(EdgeWeightedGraph G, int s, int sizeX, int sizeY) {
        // GET SIZES OF BOARD
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        inputGraph = G;

        /* START: THIS PART IS NOT TOUCHED */
        distTo = new double[G.V()];
        edgeTo = new Edge[G.V()];

        validateVertex(s);

        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
        /* END: THIS PART IS NOT TOUCHED */
        // RELAX VERTICES IN ORDER OF DISTANCE FROM S
        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            // GET VALID KNIGHT MOVES OF VERTEX v
            // AND NAIVELY GET THE SHORTEST DISTANCED VERTEX IN A RETURNED PATH(EDGE) SET.
            for (int w : validKnightMoves(v)) {
                /* START: THIS PART IS NOT TOUCHED */
                if (distTo[w] > distTo[v]) {
                    distTo[w] = distTo[v];
                    edgeTo[w] = new Edge(v, w, 0);
                    if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
                    else pq.insert(w, distTo[w]);
                }
                /* END: THIS PART IS NOT TOUCHED */
            }
        }
    }

    // 1:1 CORRESPONDS TO METHOD IN edu.princeton.cs.algs4. NO CHANGE HAS BEEN DONE.
    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    private Iterable<Integer> validKnightMoves(int v) {
        int row = v / sizeY;
        int col = v % sizeX;

        int[][] moves = {
                {-2, -1}, {-2, 1},   // Upward moves
                {-1, -2}, {-1, 2},   // Leftward moves
                {1, -2}, {1, 2},     // Rightward moves
                {2, -1}, {2, 1}      // Downward moves
        };

        List<Integer> validMoves = new ArrayList<>();

        for (int[] move : moves) {
            int newRow = row + move[0];
            int newCol = col + move[1];

            if (isValidSquare(newRow, newCol)) {
                validMoves.add(newRow * sizeX + newCol);
            }
        }

        return validMoves;
    }

    // HELPER METHOD TO CHECK IF THE VERTEX IS VALID AND NOT AN OBSTACLE
    private boolean isValidSquare(int row, int col) {
        return row >= 0 && row < sizeY && col >= 0 && col < sizeX && !isObstacle(row, col);
    }

    // WHEN WE ARE LOADING OUR GRAPH IN Loader CLASS, FOR TREES(A.K.A OBSTACLES), WE DON'T ASSIGN
    // ANY NEIGHBOUR VERTEX. SO OBSTACLES SHOULDN'T HAVE ANY EDGE.
    private boolean isObstacle(int row, int col) {
        int id = (row * sizeX) + col;
        int count = 0;
        for (Edge edge : inputGraph.adj(id)) {
            count++;
        }
        // CONFIRM THAT NUMBER OF EDGE IS EQUAL TO 0.
        return count == 0;
    }

    // 1:1 CORRESPONDS TO METHOD IN edu.princeton.cs.algs4. NO CHANGE HAS BEEN DONE.
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    // 1:1 CORRESPONDS TO METHOD IN edu.princeton.cs.algs4. NO CHANGE HAS BEEN DONE.
    public Iterable<Edge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<Edge> path = new Stack<Edge>();
        int x = v;
        for (Edge e = edgeTo[v]; e != null; e = edgeTo[x]) {
            path.push(e);
            x = e.other(x);
        }
        return path;
    }
}
