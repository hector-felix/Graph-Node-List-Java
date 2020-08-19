/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc401project2;

/**
 *
 * @author Hector Felix
 */
//Java code for shortest path with path description

import java.util.*;

public class Graph {

    private int numElements;
    private int[][] m;
    private boolean[][] visitedMatrix;

    public Graph(int numElements) {
        this.numElements = numElements;
        m = new int[numElements][numElements];
        visitedMatrix = new boolean[numElements][numElements];
    }

    
    public void addEdge(int src, int dest, int weight) {
        int runningWeight = weight;
        m[src][dest] = runningWeight;
        m[dest][src] = runningWeight;
        visitedMatrix[src][dest] = true;
        visitedMatrix[dest][src] = true;
    }

    public void printConnections() {
        boolean[][] emptyList = new boolean[numElements][numElements];

        System.out.print("Node List: {");
        for (int i = 1; i < numElements; i++) {
            System.out.print(i + ",");
        }
        System.out.println(numElements + "}"); 

        System.out.print("Edge List: {");
        for (int i = 0; i < numElements; i++) 
        {
            for (int j = 0; j < numElements; j++) 
            {
                if (visitedMatrix[i][j] && !emptyList[i][j] && j < numElements - 1) {
                    System.out.print("(" + (i + 1) + ",");
                    System.out.print((j + 1) + ",");
                    System.out.print(m[i][j]);
                    System.out.print("),");
                    emptyList[j][i] = true; 
                } else if (visitedMatrix[i][j] && !emptyList[i][j]) {
                    System.out.print("(" + (i + 1) + ",");
                    System.out.print((j + 1) + ",");
                    System.out.print(m[i][j]);
                    System.out.print(")");
                    emptyList[j][i] = true; 
                }
            }
            if (i < numElements - 2) {
                System.out.print(",");
            }
        }
        System.out.println("}");

    }

    public boolean hasEdge(int src, int dest) {
        return visitedMatrix[src][dest];
    }

    public void printWeight(int dist[], int src, int dest) {
        int V = numElements;

        System.out.println("\nWidest Path Weight:");
        System.out.println((src + 1) + " ---> "+(dest+1)+" \t\t " + dist[dest]);
        
//        for (int i = 0; i < V; i++) {
//            System.out.println((i + 1) + " \t\t " + dist[i]);
//        }

    }

    public int minDistance(int dist[], Boolean[] visitedArray) {
        int V = numElements;
        int indexL = -1;
        
        int min = Integer.MAX_VALUE;
        

        for (int v = 0; v < V; v++) {
            if (visitedArray[v] == false && dist[v] <= min) {
                min = dist[v];
                indexL = v;
            }
        }
        return indexL;
    }

    public int[][] getMatrix() {
        return m;
    }

    public void dijkstra(int graph[][], int src, int dest) {
        int V = numElements;
        Boolean[] visitedNodes = new Boolean[V];
        int dist[] = new int[V]; 
        
        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MIN_VALUE; // change to minus infinity as opposed to shortest algorithm
            visitedNodes[i] = false;
        }

        dist[src] = 0;
//        dist[src] = Integer.MAX_VALUE;

        int[] currentPath = new int[V]; 
        currentPath[src] = -1;

        for (int i = 1; i < V; i++) {
            int neighborNode = -1;
            int widestDistance = Integer.MIN_VALUE;
            for (int nodeIndex = 0; nodeIndex < V; nodeIndex++) {
                if (dist[nodeIndex] > widestDistance && !visitedNodes[nodeIndex]) {
                    neighborNode = nodeIndex;
                    widestDistance = dist[nodeIndex];
                }
            }
            visitedNodes[neighborNode] = true;

            for (int nodeIndex = 0; nodeIndex < V; nodeIndex++) {
                int edge = m[neighborNode][nodeIndex];

                if (edge > 0 && ((widestDistance + edge) > dist[nodeIndex])) {
                    currentPath[nodeIndex] = neighborNode;
                    dist[nodeIndex] = widestDistance + edge;
                    //System.out.println("Parents: " + Arrays.toString(visitedList));

                }
            }
        }
//        System.out.println("Parents: " + Arrays.toString(visitedList));
//        System.out.println("DIST: " + Arrays.toString(dist));

        printWeight(dist,src,dest);
        //printSolution(src, dist, visitedList);        // Almost got the path printing working...
    }
    private static void printPath1(int currentVertex, int[] visitedList) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < visitedList.length; i++) {
            stack.push(currentVertex);
        }

        while (!stack.isEmpty()) {

        }
    }

}
