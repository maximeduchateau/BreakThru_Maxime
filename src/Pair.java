import java.util.List;

public class Pair {
    private Integer value;
    private List<PairTuple> path;

    public Pair(Integer value, List<PairTuple> path){
        this.value = value;
        this.path = path;

    }

    public List<PairTuple> getPath() {
        return path;
    }

    public Integer getValue() {
        return value;
    }

}
