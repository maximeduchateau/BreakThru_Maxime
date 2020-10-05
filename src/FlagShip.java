public class FlagShip extends Ship {
    private Boolean team;

    public FlagShip(Boolean team) {
        super(team);
        this.team = team;
    }

    public String toString() {
        return "F";
    }

    public Integer isValidMove(int srcX, int srcY, int dstX, int dstY, Board board) {

        boolean validMove = true;
        //not valid if the destination position equals the beginningposition.
        //also not valid if both the X and Y coordinates change (meaning that is is not a horizontal or vertical move)
        if (srcX != dstX && srcY != dstY) {
            validMove = false;
        }
        //use noPieceInWay function below to check whether there are no boats in the way
        if (!noPieceInWay(srcX, srcY, dstX, dstY, board)) {
            validMove = false;

        }

        if (validMove) {
            return 2;
        } else {
            return -1;
        }
    }

public int value(){
        return 1000;
}
    public int positionValue(int row, int column) {
        int[][] positionValue =
                {{1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000,1000, 1000, 1000},
                        {1000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1000},
                        {1000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1000},
                        {1000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1000},
                        {1000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1000},
                        {1000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1000},
                        {1000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1000},
                        {1000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1000},
                        {1000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1000},
                        {1000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1000},
                        {1000, 1000, 1000, 1000, 1000, 1000, 1000,1000, 1000, 1000, 1000}};

        return positionValue[row][column];
    }


    public int maximizeDistanceFromMiddle(int row,int column) {

            return (int) (30*Math.pow(row-5, 2)+30*Math.pow(column-5,2));
        }

}
