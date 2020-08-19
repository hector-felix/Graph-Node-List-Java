package csc401project2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Hector Felix
 */

import java.util.Arrays;

public class Graph1 {

    private int numOfNodes;
    private boolean directed;
    private boolean weighted;
    private int[][] matrix;
    static int graphLength = 0;

    /*
     This will allow us to safely add weighted graphs in our class since
     we will be able to check whether an edge exists without relying
     on specific special values (like 0)
     */
    private boolean[][] isSetMatrix;

    // ...
    public Graph1(int numOfNodes, boolean directed, boolean weighted) {

        this.directed = directed;
        this.weighted = weighted;
        this.numOfNodes = numOfNodes;

        // Simply initializes our adjacency matrix to the appropriate size
        matrix = new int[numOfNodes][numOfNodes];
        isSetMatrix = new boolean[numOfNodes][numOfNodes];
    }

    public void addEdge(int source, int destination) {

        int valueToAdd = 1;

        if (weighted) {
            valueToAdd = 0;
        }

        matrix[source][destination] = valueToAdd;
        isSetMatrix[source][destination] = true;

        if (!directed) {
            matrix[destination][source] = valueToAdd;
            isSetMatrix[destination][source] = true;
            //isSetMatrix[source][destination] = true;
        }
    }

    public void addEdge(int source, int destination, int weight) {

        int valueToAdd = weight;

        if (!weighted) {
            valueToAdd = 1;
        }

        matrix[source][destination] = valueToAdd;
        isSetMatrix[source][destination] = true;

        if (!directed) {
            matrix[destination][source] = valueToAdd;
            isSetMatrix[destination][source] = true;
        }
    }

    public void printMatrix() {
        for (int i = 0; i < numOfNodes; i++) {
            for (int j = 0; j < numOfNodes; j++) {
                // We only want to print the values of those positions that have been marked as set
                if (isSetMatrix[i][j]) {
                    System.out.format("%8s", String.valueOf(matrix[i][j]));
                } else {
                    System.out.format("%8s", "/  ");
                }
            }
            System.out.println();
        }
    }

    /*
    We look at each row, one by one.
    When we're at row i, every column j that has a set value represents that an edge exists from
    i to j, so we print it
     */
    public void printEdges() {
        for (int i = 0; i < numOfNodes; i++) {
            System.out.print("Node " + (i + 1) + " is connected to: ");
            for (int j = 0; j < numOfNodes; j++) {
                if (isSetMatrix[i][j]) {
                    System.out.print((j + 1) + " ");
                }
            }
            System.out.println();
        }
    }

    public void printEdgeList() {
        boolean[][] noPrintMatrix = new boolean[numOfNodes][numOfNodes];

        System.out.print("Node List: {");
        for (int i = 1; i < numOfNodes; i++) {
            System.out.print(i + ",");
        }
        System.out.println(numOfNodes + "}"); // Node List: {1, 2, 3, 4, 5}

        System.out.print("Edge List: {");
        for (int i = 0; i < numOfNodes; i++) // i = Nodes
        {
            for (int j = 0; j < numOfNodes; j++) // j = Neighbor
            {
                if (isSetMatrix[i][j] && !noPrintMatrix[i][j]) {
                    System.out.print("(" + (i + 1) + ",");
                    System.out.print((j + 1) + ",");
                    System.out.print(matrix[i][j]); // Weight
                    System.out.print(")");

                    noPrintMatrix[j][i] = true; // Basically here to make sure that we dont print (1-> 4) and (4 -> 1), removing duplicate edges
                }

            }
        }
        System.out.println("}");

    }

    public void dijkstraWidest(int source, int dest) {

    }

    public boolean hasEdge(int source, int destination) {
        return isSetMatrix[source][destination];
    }

    public Integer getEdgeValue(int source, int destination) {
        if (!weighted || !isSetMatrix[source][destination]) {
            return null;
        }
        return matrix[source][destination];
    }

    // A utility function to find the vertex with minimum distance value, 
    // from the set of vertices not yet included in shortest path tree 
    //static final int V = 9;

    public int minDistance(int dist[], Boolean sptSet[], int n) {
        // Initialize min value 
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < n; v++) {
            if (sptSet[v+1] == false && dist[v+1] <= min) {
                min = dist[v+1];
                min_index = v;
            }
        }

        return min_index;
    }
    
    public int[][] getMatrix() 
   {
	return matrix;
   }
    
    void printSolution(int source, int dist[], int n, StringBuilder path[]) {
        System.out.println("Vertex Distance from Source");
        for (int i = 0; i < graphLength; i++) {
            System.out.print("\n" + source + "->" + (i+1) + " " + dist[i+1] + " " + path[i]);
        }
    }

    // A utility function to print the constructed distance array 
//    public void printSolution(int dist[], int n, int dest) {
//        System.out.println("Vertex \t\t Distance from Source");
//        //System.out.println(n + " \t\t " + dist[dest]);
//        
//        for (int i = 0; i < n; i++) {
//            System.out.println((i+1) + " \t\t " + dist[i+1]);
//        }
//    }

    // Function that implements Dijkstra's single source shortest path 
    // algorithm for a graph represented using adjacency matrix 
    // representation 
    public void Dijkstra(int graph[][], int src, int n, int dest) {
        
        graphLength = graph.length;
        
        System.out.println("N: "+(n+1));
        System.out.println("GL: "+graphLength);
        
        StringBuilder path[] = new StringBuilder[graphLength];
        int dist[] = new int[graphLength+1]; // The output array. dist[i] will hold 
        // the shortest distance from src to i 

        // sptSet[i] will true if vertex i is included in shortest 
        // path tree or shortest distance from src to i is finalized 
        Boolean sptSet[] = new Boolean[graphLength+1];

        // Initialize all distances as INFINITE and stpSet[] as false 
        for (int i = 0; i < graphLength; i++) {
            path[i] = new StringBuilder("");
            dist[i+1] = Integer.MAX_VALUE;
            sptSet[i+1] = false;
        }

        // Distance of source vertex from itself is always 0 
        dist[src+1] = 0;
        path[src+1].append(src+1);

        // Find shortest path for all vertices 
        for (int count = 0; count < n - 1; count++) {
            // Pick the minimum distance vertex from the set of vertices 
            // not yet processed. u is always equal to src in first 
            // iteration. 
            int u = minDistance(dist, sptSet, graphLength);

            // Mark the picked vertex as processed 
            sptSet[u+1] = true;

            // Update dist value of the adjacent vertices of the 
            // picked vertex. 
            for (int v = 0; v < n-1; v++) // Update dist[v] only if is not in sptSet, there is an 
            // edge from u to v, and total weight of path from src to 
            // v through u is smaller than current value of dist[v] 
            {
                if (!sptSet[v+1] && graph[u][v] != 0 && dist[u+1] != Integer.MAX_VALUE && dist[u+1] + graph[u][v] < dist[v+1]) {
                    dist[v+1] = dist[u+1] + graph[u][v];
                    path[v+1] =  new StringBuilder();
                    path[v+1].append(path[u]).append(v);
                }
            }
        }
        
//        System.out.println(Arrays.toString(dist));
//        System.out.println("Nodes: "+Arrays.toString(path));

        // print the constructed distance array 
//        printSolution(dist, n, dest);
        printSolution(src+1, dist, graphLength+1, path);
    }
}
