import java.util.List;

public class TTEntry {
    private int value;
    private int alpha;
    private int beta;
    private int depth;
    private List<PairTuple> path;

    public TTEntry(int value, int alpha, int beta, int depth, List<PairTuple> path) {
        this.value = value;
        this.alpha = alpha;
        this.beta = beta;
        this.depth = depth;
        this.path = path;


    }

    public int getValue() {
        return value;
    }


    public int getDepth() {
        return depth;
    }

    public List<PairTuple> getPath() {
        return path;
    }

    public int getAlpha() {
        return alpha;
    }

    public int getBeta() {
        return beta;
    }

    @Override
    public String toString() {
        return "TTEntry{" +
                "value=" + value +
                ", alpha=" + alpha +
                ", beta=" + beta +
                ", depth=" + depth +
                ", path=" + path +
                '}';
    }
}
