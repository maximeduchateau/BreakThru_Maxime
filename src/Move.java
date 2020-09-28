public class Move {
    private int srcX;
    private int srcY;
    private int dstX;
    private int dstY;
    private int weight;

    public Move(int srcX, int srcY, int dstX, int dstY, int weight){
        this.srcX=srcX;
        this.srcY=srcY;
        this.dstX=dstX;
        this.dstY=dstY;
        this.weight=weight;
    }

    public int getSrcX(){
        return srcX;
    }

    public int getSrcY() {
        return srcY;
    }

    public int getDstX() {
        return dstX;
    }

    public int getDstY() {
        return dstY;
    }

    public int getWeigt() {
        return weight;
    }

    @Override
    public String toString() {
        return "Move{" +
                "srcX=" + srcX +
                ", srcY=" + srcY +
                ", dstX=" + dstX +
                ", dstY=" + dstY +
                ", weight=" + weight +
                '}';
    }
}

