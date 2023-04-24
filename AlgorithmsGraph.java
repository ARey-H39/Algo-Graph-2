import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class AlgorithmsGraph {
    public static void main(String[] args) throws IOException {
        Graph graph = new Graph();

        File input = new File("input.txt");
        Scanner inputFile = new Scanner(input);
        while (inputFile.hasNextInt()) {
            int nodeIndex = inputFile.nextInt();
            int adjIndex = inputFile.nextInt();
            graph.addEdge(nodeIndex, adjIndex);
        }
        inputFile.close();

        graph.adjustRevAdjList();
        graph.initializeRank();
        graph.computeRank();
        graph.displayGraph();
        graph.getTotalRank();
        graph.saveGraph();
    }
}
