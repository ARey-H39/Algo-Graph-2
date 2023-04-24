import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Graph {
    private final HashSet<Node> nodes = new HashSet<>();
    private final HashMap<Node, List<Node>> adjList = new HashMap<>();
    private final HashMap<Node, List<Node>> revAdjList = new HashMap<>();

    public void addEdge(int outNode, int inNode) {
        if (!checkNode(outNode)) createNode(outNode);
        if (!checkNode(inNode)) createNode(inNode);
        if (checkNode(outNode) && checkNode(inNode)) {
            createEdge(getNode(outNode), getNode(inNode), false);
            createEdge(getNode(inNode), getNode(outNode), true);
        }
    }

    public void displayGraph() {
        for (Node node : nodes) {
            System.out.println(
                    "Vertex " + node.getIndex() + ": rank = " + node.getRank() +
                            ", out-degree = " + node.getOutDegree() + ", rev-degree = " + node.getRevDegree()
            );
            System.out.println(
                    "Edges from " + node.getIndex() + " to: " +
                            adjListString(adjList.get(node)).replace(" ", ", ")
            );

            System.out.println(
                    "Edges leading to " + node.getIndex() + ": " +
                            adjListString(revAdjList.get(node)).replace(" ", ", ")
            );

            System.out.println();
        }
    }

    public void saveGraph() throws IOException {
        PrintWriter output = new PrintWriter("output.txt");
        for (Node node : nodes) {
            output.println(
                    node.getIndex() + " " +
                    node.getOutDegree() + " " +
                    adjListString(adjList.get(node))
            );
        }
        output.close();
    }

    private void createNode(int vertexId) {
        Node node = new Node(vertexId);
        nodes.add(node);
        adjList.put(node, new ArrayList<>());
        revAdjList.put(node, new ArrayList<>());
    }

    private boolean checkNode(int vertexId) {
        boolean nodeExists = false;

        Node node = new Node(vertexId);

        if (nodes.contains(node)) nodeExists = true;

        return nodeExists;
    }

    private Node getNode(int index) {
        Node result = null;
        for (Node node : nodes) {
            if (node.getIndex() == index) result = node;
        }
        return result;
    }

    private void createEdge(Node parentNode, Node adjNode, boolean reversed) {

        HashMap<Node, List<Node>> activeAdjList;

        if (reversed) activeAdjList = revAdjList;
        else activeAdjList = adjList;

        activeAdjList.get(parentNode).add(adjNode);

        adjustDegrees(parentNode, reversed);
    }

    private void adjustDegrees(Node node, boolean reversed) {
        if (reversed) {
            node.addRevDegree();
        } else {
            node.addOutDegree();
        }
    }

    private String adjListString(List<Node> adjList) {
        String output = "";
        for (Node node : adjList) {
            output += node.getIndex() + " ";
        }
        return output.trim();
    }

    public void initializeRank() {
        for (Node node : nodes) {
            node.setRank(1.0 / Math.abs(nodes.size()));
        }
    }

    public void computeRank() {
        for (Node node : nodes) {
            double newRank = 0.0;
            for (Node adjNode : revAdjList.get(node)) {
                newRank += adjNode.getRank();
            }
            newRank = 0.9 * newRank + 0.1 * (1 / Math.abs(nodes.size()));
            node.setRank(newRank);
        }

//        for (int i = 1; i <= 50; i++) {
//            for (Node node : nodes) {
//                double sum = 0.0;
//                for (Node adjNode : revAdjList.get(node)) {
//                    double calc = adjNode.getRank() / adjNode.getRevDegree();
//                    sum += calc;
//                }
//                node.setRank(0.9 * sum + (0.1 / Math.abs(nodes.size())));
//            }
//        }

//        for (int i = 1; i <= 50; i++) {
//            double finalRank = 0.0;
//            for (Node node : nodes) {
//                double sum = 0.0;
//                for (Node adjNode : revAdjList.get(node)) {
//                    double calculation = node.getRank() / adjNode.getRevDegree();
//                    sum += calculation;
//                }
//            }
//        }
    }

    public void adjustRevAdjList() {
        boolean zeroDegreeFound = false;

        for (Node node : nodes) {
            if (node.getOutDegree() == 0 || node.getRevDegree() == 0)
                zeroDegreeFound = true;
        }

        if (zeroDegreeFound) {
            int max = 0;
            for (Node node : nodes) {
                if (node.getIndex() > max) max = node.getIndex();
            }
            createNode(max + 1);

            for (Node node : nodes) {
                if (node.getIndex() == max + 1) continue;

                createEdge(node, getNode(max + 1), false);
                createEdge(getNode(max + 1), node, true);
            }
        }
    }

    public void getTotalRank() {
        double total = 0.0;
        for (Node node : nodes) {
            total += node.getRank();
        }
        System.out.println("All ranks total to: " + total);
    }
}
