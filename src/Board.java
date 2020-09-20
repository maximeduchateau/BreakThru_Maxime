public class Board {
    private Ship[][] board;
    private Boolean teamWon;


    public Board() {
        this.board = new Ship[11][11];
        this.teamWon = null;
    }

    public Ship[][] getGameBoard() {
        return this.board;
    }

    public void initialize() {
        // initialize silver ships

        board[1][3] = new Ship(false);
        board[1][4] = new Ship(false);
        board[1][5] = new Ship(false);
        board[1][6] = new Ship(false);
        board[1][7] = new Ship(false);
        board[9][3] = new Ship(false);
        board[9][4] = new Ship(false);
        board[9][5] = new Ship(false);
        board[9][6] = new Ship(false);
        board[9][7] = new Ship(false);
        board[3][1] = new Ship(false);
        board[3][9] = new Ship(false);
        board[4][1] = new Ship(false);
        board[4][9] = new Ship(false);
        board[5][1] = new Ship(false);
        board[5][9] = new Ship(false);
        board[6][1] = new Ship(false);
        board[6][9] = new Ship(false);
        board[7][1] = new Ship(false);
        board[7][9] = new Ship(false);

        //initialize golden ships
        board[3][4] = new Ship(true);
        board[3][5] = new Ship(true);
        board[3][6] = new Ship(true);
        board[7][4] = new Ship(true);
        board[7][5] = new Ship(true);
        board[7][6] = new Ship(true);
        board[4][3] = new Ship(true);
        board[5][3] = new Ship(true);
        board[6][3] = new Ship(true);
        board[4][7] = new Ship(true);
        board[5][7] = new Ship(true);
        board[6][7] = new Ship(true);

        //initialize Flagship
        board[5][5] = new FlagShip(true);
    }

    public Boolean setTeamWon() {
        return teamWon;
    }

    public void setTeamWon(Boolean updateWinner) {
        this.teamWon = updateWinner;
    }

    public void move(int srcX, int srcY, int dstX, int dstY) {
        if (board[dstX][dstY] instanceof FlagShip) {
            teamWon = board[srcX][srcY].getTeam();
        }
        board[dstX][dstY] = board[srcX][srcY];
        board[srcX][srcY] = null;
    }
}


