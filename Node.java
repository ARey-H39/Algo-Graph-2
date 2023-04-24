import java.util.Objects;

public class Node {
    private int index = 0;
    private double rank = -1;
    private int revDegree = 0;
    private int outDegree = 0;

    public Node(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public double getRank() {
        return this.rank;
    }

    public int getOutDegree() {
        return outDegree;
    }

    public int getRevDegree() { return revDegree; }

    public void addRevDegree() {
        revDegree++;
    }

    public void addOutDegree() {
        outDegree++;
    }

    public void setRank(double rank) { this.rank = rank; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return index == node.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
}
