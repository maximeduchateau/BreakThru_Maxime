public class PairTuple {
    private Move move;
    private int depth;


    public PairTuple(Move move, int depth) {
        this.move = move;
        this.depth = depth;
    }

    public Move getMove() {
        return move;
    }

    public int getDepth() {
        return depth;
    }

    @Override
    public String toString() {
        return "PairTuple{" +
                "move=" + move +
                ", depth=" + depth +
                '}';
    }
}

