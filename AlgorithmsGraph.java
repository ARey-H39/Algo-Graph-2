import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
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

        graph.initialize();

        menu(graph);
    }

    public static void menu(Graph graph) throws IOException {
        Scanner keyboard = new Scanner(System.in);
        int input = 0;

        while (input != 3) {

            System.out.println("-------------------------");
            System.out.println("    Algorithms Graph");
            System.out.println("-------------------------");
            System.out.println();
            System.out.println("1) Print Output");
            System.out.println("2) Save Output to file");
            System.out.println("3) Exit");
            System.out.println();
            System.out.print("How would you like to output the graph?: ");

            if (keyboard.hasNextInt()) input = keyboard.nextInt();
            else {
                keyboard.next();
                input = 0;
            }

            System.out.println();

            switch (input) {
                case 1:
                    System.out.println("----------Graph Output----------");
                    graph.displayGraph();
                    System.out.println("--------------------------------\n");
                    break;
                case 2:
                    graph.saveGraph();
                    System.out.println("-- Graph saved to Pagerank.txt --\n");
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("! Invalid Input: Try using an option from above. !\n");
            }
        }
    }
}
