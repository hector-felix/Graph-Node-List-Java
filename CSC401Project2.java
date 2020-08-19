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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CSC401Project2 {

    public static void main(String[] args) throws IOException {

        InputStreamReader rdr = new InputStreamReader(System.in);
        BufferedReader console = new BufferedReader(rdr);
        boolean prob[] = {true, true, false, false}; // 50% for more connections
        System.out.println("Number of nodes: ");
        int n = Integer.parseInt(console.readLine());
        int high = (n * 5) + 1;
        int low = 1;

        Graph myGraph = new Graph(n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    int rand = (int) (Math.random() * 4);

                    if (!myGraph.hasEdge(i, j)) {
                        if (prob[rand]) {
                            int weight = (int) (Math.random() * (high - low) + low);
                            myGraph.addEdge(i, j, weight);
                        }
                    }
                }
            }
        }

        myGraph.printConnections();
        System.out.println("Please enter source node: ");
        int source = ((Integer.parseInt(console.readLine())) - 1);
        System.out.println("Please enter destination node: ");
        int dest = ((Integer.parseInt(console.readLine())) - 1);
        myGraph.dijkstra(myGraph.getMatrix(), source, dest);
    }

}
