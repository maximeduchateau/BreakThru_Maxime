
import java.util.Arrays;

public class Board {
    public static final int BOARD_DIM = 11;
    private Ship[][] board;
    private Boolean teamWon;
    public static final int MAX_WEIGHT_PER_TURN = 2;
    private int flagX;
    private int flagY;

    public Board() {
        this.board = new Ship[BOARD_DIM][BOARD_DIM];
        this.teamWon = null;
    }

    public Ship getPosition(int posX, int posY) {
        return board[posX][posY];
    }

    public void setPosition(int posX, int posY, Ship ship) {
        board[posX][posY] = ship;
    }

    public Boolean getTeamWon() {
        return teamWon;
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

    public void move(int srcX, int srcY, int dstX, int dstY) {
        this.board[dstX][dstY] = this.board[srcX][srcY];
        this.board[srcX][srcY] = null;
        if (silverWinningCondition() || goldWinningCondition()) {
            System.out.println("!!!!!!!GAME OVER!!!!!!!!");
            //teamWon = board[dstX][dstY].getTeam();
        }
    }

    public Boolean silverWinningCondition() {
        for (int i = 0; i < BOARD_DIM; ++i) {
            for (int j = 0; j < BOARD_DIM; ++j) {
                if (board[i][j] != null && board[i][j] instanceof FlagShip) {
                    return false;
                }
            }
        }
        return true;
    }
//    public Boolean draw() {
//        int counter=0;
//        for (int i = 0; i < BOARD_DIM; ++i) {
//            for (int j = 0; j < BOARD_DIM; ++j) {
//                if (board[i][j] != null && board[i][j].getTeam()) {
//                    ++counter;
//                    if (counter>1){
//                        return false;}
//                }
//            }
//        }
//        return true;
//    }


    public Boolean goldWinningCondition() {
        for (int i = 0; i < BOARD_DIM; i++) {
            if ((board[i][BOARD_DIM - 1] != null && board[i][BOARD_DIM - 1] instanceof FlagShip)
            ||(board[i][0] != null && board[i][0] instanceof FlagShip)
            ||(board[BOARD_DIM - 1][i] != null && board[BOARD_DIM - 1][i] instanceof FlagShip)
            ||(board[0][i] != null && board[0][i] instanceof FlagShip)) {
                return true;
            }
        }
            return false;
        }

    @Override
    public String toString() {
        return "Board{" +
                "board=" + Arrays.toString(board) +
                '}';
    }

    public void printBoard(Board board) {

        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+");
        int row, column;
        for (row = 0; row < Board.BOARD_DIM; row++) {
            if (row < 2) {
                System.out.print((11 - row) + "|");
            } else {
                System.out.print(" " + (11 - row) + "|");
            }
            for (column = 0; column < Board.BOARD_DIM; column++) {
                //if there is no boat: empty position is printed
                if (board.getPosition(row, column) == null) {
                    System.out.print(" |");
                } else {
                    System.out.print(board.getPosition(row, column).toString() + "|");
                }
            }
            System.out.println("");

        }
        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+");
        System.out.println("  +a+b+c+d+e+f+g+h+i+j+k+");

    }

    static int evaluate(Board board) {
        board.getFlag();
        int boardRating = 0;
        for (int i = 0; i < board.BOARD_DIM; ++i) {
            for (int j = 0; j < board.BOARD_DIM; ++j) {
                Ship ship = board.getPosition(i, j);
                if (ship == null) {
                    continue;
                }
                if (board.getPosition(i,j) instanceof FlagShip) {
                    boardRating+=ship.positionValue(i,j)+ship.value()+((FlagShip) board.getPosition(i,j)).maximizeDistanceFromMiddle(i,j);
                }
                else{
                if (ship.getTeam()) {
                    boardRating += ship.value()+0.5*ship.positionValue(i,j)+ 0.6*board.RewardForProximityToFlag(i,j);
                } else {
                    boardRating -= ship.value()+ship.positionValue(i,j)+board.RewardForProximityToFlag(i,j);
                }
            }}
        }
        return boardRating;

    }
    public int RewardForProximityToFlag (int i, int j){
        return (int) (15*(10-Math.sqrt(Math.pow(i-flagX,2)+Math.pow(j-flagY,2))));
    }

    private void getFlag() {
        for (int i = 0; i < BOARD_DIM; ++i) {
            for (int j = 0; j < BOARD_DIM; ++j) {
                if (board[i][j] instanceof FlagShip) {
                    this.flagX=i;
                    this.flagY=j;
                }
    }
}}}