import java.util.List;

public class Pair {
    private Integer value;
    private List<Move> path;
    public Pair(Integer value, List<Move> path){
        this.value = value;
        this.path = path;
    }

    public List<Move> getPath() {
        return path;
    }

    public Integer getValue() {
        return value;
    }
}
