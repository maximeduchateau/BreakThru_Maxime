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

    public Integer isValidMove(int srcX, int srcY, int dstX, int dstY, Ship[][] board) {

        boolean validMove = true;
        //not valid if the destination position equals the beginningposition.
        //also not valid if both the X and Y coordinates change (meaning that is is not a horizontal or vertical move)
        if ((srcX == dstX && srcY == dstY) || (srcX != dstX && srcY != dstY)) {
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

    //checks wheter there are no chesspieces at destination coordinates or on the way over there.
    public boolean noPieceInWay(int srcX, int srcY, int dstX, int dstY, Ship[][] board) {
        boolean noPieceInWay = true;
        // case for vertical moves
        if (srcX != dstX) {
            //Vertical case and the destination X coordinate is larger than the source X coordinate
            if (dstX > srcX) {
                for (int i = 1; i <= dstX - srcX; i++) {
                    if (!(board[srcX + i][srcY] == null)) {
                        noPieceInWay = false;
                        break;
                    }
                }
                //Vertical case and the destination X coordinate is smaller than the source X coordinate
            } else {
                for (int i = 1; i <= srcX - dstX; i++) {
                    if (!(board[srcX - i][srcY] == null)) {
                        noPieceInWay = false;
                        break;
                    }

                }
            }
        }
        //case for horizontal movess
        else if (srcY != dstY) {
            //horizontal case and the destination Y coordinate is larger than the source Y coordinate
            if (dstY > srcY) {
                for (int i = 1; i <= dstY - srcY; i++) {
                    if (!(board[srcX][srcY + i] == null)) {
                        noPieceInWay = false;
                        break;
                    }
                }
                //horizontal case and the destination Y coordinate is smaller than the source Y coordinate
            } else {
                for (int i = 1; i <= srcY - dstY; i++) {
                    if (!(board[srcX][srcY - i] == null)) {
                        noPieceInWay = false;
                        break;
                    }

                }
            }
        }
        return noPieceInWay;
    }

    public Integer isValidCapture(int srcX, int srcY, int dstX, int dstY, Ship[][] board) {
        boolean validCapture = true;
        //not valid if the destination position equals the beginningposition.
        //also not valid if either x or y value remains constant (must be diagonally)
        if ((srcX == dstX && srcY == dstY) || (srcX == dstX && srcY != dstY || srcX != dstX && srcY == dstY)) {
            validCapture = false;
        }
        //capture is only valid if destination is adjacent position
        if ((Math.abs(srcX - dstX)) != 1 || Math.abs(srcY - dstY) != 1) {
            validCapture = false;
        }
        if (validCapture) {
            return 2;
        } else {
            return -1;
        }

    }


}

