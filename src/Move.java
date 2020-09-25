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
    public void setSrcX(int srcX){
        this.srcX= srcX;
    }
    public void setSrcY(int srcY){
        this.srcY= srcY;
    }
    public void setDstX(int dstX){
        this.dstX= dstX;
    }
    public void setDstY(int dstY) {
        this.dstY = dstY;
    }
    public void setWeight(int weight){
        this.weight= weight;
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
}

