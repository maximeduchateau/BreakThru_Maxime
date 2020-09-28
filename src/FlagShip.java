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
        return 300;
}
}
