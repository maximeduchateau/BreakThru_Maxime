import java.util.ArrayList;

public class Ship {

    private Boolean team;

    public Ship(Boolean team) {
        this.team = team;
    }

    public Boolean getTeam() {
        return this.team;
    }

    public void setTeam(Boolean newTeam) {
        this.team = newTeam;
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
            return 1;
        } else {
            return -1;
        }
    }


    //checks wheter there are no pieces at destination coordinates or on the way over there.
    public boolean noPieceInWay(int srcX, int srcY, int dstX, int dstY, Board board) {
        boolean noPieceInWay = true;
        // case for vertical moves
        if (srcX != dstX) {
            //Vertical case and the destination X coordinate is larger than the source X coordinate
            if (dstX > srcX) {
                for (int i = 1; i <= dstX - srcX; i++) {
                    if (!(board.getPosition(srcX + i, srcY) == null)) {
                        noPieceInWay = false;
                        break;
                    }
                }
                //Vertical case and the destination X coordinate is smaller than the source X coordinate
            } else {
                for (int i = 1; i <= srcX - dstX; i++) {
                    if (!(board.getPosition(srcX - i, srcY) == null)) {
                        noPieceInWay = false;
                        break;
                    }

                }
            }
        }
        //case for horizontal moves
        else if (srcY != dstY) {
            //horizontal case and the destination Y coordinate is larger than the source Y coordinate
            if (dstY > srcY) {
                for (int i = 1; i <= dstY - srcY; i++) {
                    if (!(board.getPosition(srcX, srcY + i) == null)) {
                        noPieceInWay = false;
                        break;
                    }
                }
                //horizontal case and the destination Y coordinate is smaller than the source Y coordinate
            } else {
                for (int i = 1; i <= srcY - dstY; i++) {
                    if (!(board.getPosition(srcX, srcY - i) == null)) {
                        noPieceInWay = false;
                        break;
                    }

                }
            }
        }
        return noPieceInWay;
    }

    public Integer isValidCapture(int srcX, int srcY, int dstX, int dstY, Board board) {
        boolean validCapture = true;

        //also not valid if either x or y value remains constant (must be diagonally)
        if (srcX == dstX && srcY != dstY || srcX != dstX && srcY == dstY) {
            validCapture = false;
        }
        //capture is only valid if destination is adjacent position
        if ((Math.abs(srcX - dstX)) != 1 || Math.abs(srcY - dstY) != 1) {
            validCapture = false;
        }
        if (board.getPosition(dstX, dstY).getTeam() == board.getPosition(srcX, srcY).getTeam()) {
            validCapture = false;
        }
        if (validCapture) {
            return 2;

        } else {
            return -1;
        }

    }

    @Override
    public String toString() {
        if (team) {
            return "G";
        } else {
            return "S";
        }
    }

//    public ArrayList<Move> generatePossibleMoves(Board board, int srcX, int srcY, int maxWeight) {
//        ArrayList<Move> possibleMoves = new ArrayList<>();
//        int moveEvaluation;
//        for (int i = 0; i < board.BOARD_DIM; ++i) {
//            for (int j = 0; j < board.BOARD_DIM; ++j) {
//                if (!(srcX == i && srcY == j)) {
//                    if (board.getPosition(i, j) != null && maxWeight==2) {
//                        moveEvaluation = board.getPosition(srcX, srcY).isValidCapture(srcX, srcY, i, j, board);
//                    } else {
//                        moveEvaluation = board.getPosition(srcX, srcY).isValidMove(srcX, srcY, i, j, board);
//                    }
//                    if (moveEvaluation > 0) {
//                        possibleMoves.add(new Move(srcX, srcY, i, j, moveEvaluation));
//                    }
//                }
//            }
//        }
//        return possibleMoves;
//    }
public ArrayList<Move> generatePossibleMoves(Board board, int srcX, int srcY, int maxWeight) {
    ArrayList<Move> possibleMoves = new ArrayList<>();
    int moveEvaluation;
    if (srcX+1<=10 && srcY+1<=10){
        if (board.getPosition(srcX+1,srcY+1) != null && maxWeight==2) {
            moveEvaluation = board.getPosition(srcX, srcY).isValidCapture(srcX, srcY, srcX+1, srcY+1, board);
            if (moveEvaluation > 0) {
                possibleMoves.add(new Move(srcX, srcY, srcX+1, srcY+1, moveEvaluation));
    }}}
        if (srcX-1>=0 && srcY+1<=10){
            if (board.getPosition(srcX-1,srcY+1) != null && maxWeight==2) {
                moveEvaluation = board.getPosition(srcX, srcY).isValidCapture(srcX, srcY, srcX-1, srcY+1, board);
                if (moveEvaluation > 0) {
                    possibleMoves.add(new Move(srcX, srcY, srcX-1, srcY+1, moveEvaluation));
                }}}
    if (srcX+1<=10 && srcY-1>=0){
        if (board.getPosition(srcX+1,srcY-1) != null && maxWeight==2) {
            moveEvaluation = board.getPosition(srcX, srcY).isValidCapture(srcX, srcY, srcX+1, srcY-1, board);
            if (moveEvaluation > 0) {
                possibleMoves.add(new Move(srcX, srcY, srcX+1, srcY-1, moveEvaluation));
            }}}
    if (srcX-1>=0 && srcY-1>=0){
        if (board.getPosition(srcX-1,srcY-1) != null && maxWeight==2) {
            moveEvaluation = board.getPosition(srcX, srcY).isValidCapture(srcX, srcY, srcX-1, srcY-1, board);
            if (moveEvaluation > 0) {
                possibleMoves.add(new Move(srcX, srcY, srcX-1, srcY-1, moveEvaluation));
            }}}

    for (int i = 1; i < board.BOARD_DIM; ++i) {
        if( srcX+i<=10 && board.getPosition(srcX+i,srcY)==null){
                    moveEvaluation = board.getPosition(srcX, srcY).isValidMove(srcX, srcY, srcX+i, srcY, board);
                if (moveEvaluation > 0) {
                    possibleMoves.add(new Move(srcX, srcY,srcX+i, srcY, moveEvaluation));
                }
            }else {break;}
        }
    for (int i = 1; i < board.BOARD_DIM; ++i) {
        if( srcY+i<=10 && board.getPosition(srcX,srcY+i)==null){
            moveEvaluation = board.getPosition(srcX, srcY).isValidMove(srcX, srcY, srcX, srcY+i, board);
            if (moveEvaluation > 0) {
                possibleMoves.add(new Move(srcX, srcY,srcX, srcY+i, moveEvaluation));
            }
        }else{break;}
    }
    for (int i = 1; i < board.BOARD_DIM; ++i) {
        if( srcX-i>=0 && board.getPosition(srcX-i,srcY)==null){
            moveEvaluation = board.getPosition(srcX, srcY).isValidMove(srcX, srcY, srcX-i, srcY, board);
            if (moveEvaluation > 0) {
                possibleMoves.add(new Move(srcX, srcY,srcX-i, srcY, moveEvaluation));
            }
        }else {break;}
    }
    for (int i = 1; i < board.BOARD_DIM; ++i) {
        if( srcY-i>=0 && board.getPosition(srcX,srcY-i)==null){
            moveEvaluation = board.getPosition(srcX, srcY).isValidMove(srcX, srcY, srcX, srcY-i, board);
            if (moveEvaluation > 0) {
                possibleMoves.add(new Move(srcX, srcY,srcX, srcY-i, moveEvaluation));
            }
        }else{break;}
    }

    return possibleMoves;
}
    public int value(){
        if (this.team==true){return 100;}
        else {return 60;}
    }

}

