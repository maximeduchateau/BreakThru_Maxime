import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class BoardTest {
        private Boolean team;
        private int depth = 2;
        private long[][] zobrist = new long[121][3];
        private HashMap<Boolean, HashMap<Long, TTEntry>> transpositionTable;

        public static final int BOARD_DIM = 11;
        private Ship[][] board;
        private Boolean teamWon;
        public static final int MAX_WEIGHT_PER_TURN = 2;


        public BoardTest() {
            this.board = new Ship[BOARD_DIM][BOARD_DIM];
            this.teamWon = null;
            fillArray();
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
            board[10][6] = new Ship(false);
            board[10][7] = new Ship(false);
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
            System.out.println("move is processed in movefunction of board class");
            this.board[dstX][dstY] = this.board[srcX][srcY];
            System.out.println("ship reached destination");
            this.board[srcX][srcY] = null;
            System.out.println("old source position is empty");
            if (silverWinningCondition() || goldWinningCondition()) {
                System.out.println("!!!!!!!GAME OVER!!!!!!!!");
                // teamWon = board[dstX][dstY].getTeam();
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


        public Boolean goldWinningCondition() {
            for (int i = 0; i < BOARD_DIM; i++) {
                if (board[i][BOARD_DIM - 1] != null && board[i][BOARD_DIM - 1] instanceof FlagShip) {
                    return true;
                }
                if (board[i][0] != null && board[i][0] instanceof FlagShip) {
                    return true;
                }
                if (board[BOARD_DIM - 1][i] != null && board[BOARD_DIM - 1][i] instanceof FlagShip) {
                    return true;
                }
                if (board[0][i] != null && board[0][i] instanceof FlagShip) {
                    return true;
                }
            }
            {
                return false;
            }
        }

        @Override
        public String toString() {
            return "Board{" +
                    "board=" + Arrays.toString(board) +
                    '}';
        }

        public void printBoard(BoardTest board) {

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
                System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+");
            }
            System.out.println("  +a+b+c+d+e+f+g+h+i+j+k+");

        }

        static int evaluate(Board board) {
            int boardRating = 0;
            for (int i = 0; i < board.BOARD_DIM; ++i) {
                for (int j = 0; j < board.BOARD_DIM; ++j) {
                    Ship ship = board.getPosition(i, j);
                    if (ship == null) {
                        continue;
                    }
                    if (ship.getTeam()) {
                        boardRating += ship.value();
                    } else {
                        boardRating -= ship.value();
                    }
                }
            }
            return boardRating;
        }



    public static long random64() {
        SecureRandom random = new SecureRandom();
        return random.nextLong();
    }

//    public long getPosition(int pieceType, int boardPos) {
//        return zobrist[pieceType][boardPos];
//    }

    public void fillArray() {
        for (int boardPos = 0; boardPos < 121; boardPos++) {
            for (int pieceType = 0; pieceType < 3; pieceType++) {

                zobrist[boardPos][pieceType] = random64();
            }
        }
    }

     long computeKey(Board board) {
        long hashKey = 0;
        for (int i = 0; i < board.BOARD_DIM; i++) {
            for (int j = 0; j < board.BOARD_DIM; j++) {

                if (board.getPosition(i, j) == null) {
                    continue;
                }
                int shipType = getShipType(i, j, board);


                int position = i * 11 + j;
                //int turn = onTurn ? 0 : 1;
                hashKey ^= zobrist[position][shipType];
            }
        }
        return hashKey;
    }

    public long updateKey(long oldKey, Move move, Board board) {
        int destPos = move.getDstX() * 11 + move.getDstY();
        int srcPos = move.getSrcX() + 11 + move.getSrcY();
        int dstShipType = getShipType(move.getDstX(), move.getDstY(), board);
        int srcShipType = getShipType(move.getSrcX(), move.getSrcY(), board);
        if (move.getWeight() == board.MAX_WEIGHT_PER_TURN) {
            return oldKey ^ zobrist[destPos][dstShipType] ^ zobrist[destPos][srcShipType] ^ zobrist[srcPos][srcShipType];
        } else {
            return oldKey ^ zobrist[destPos][srcShipType] ^ zobrist[srcPos][srcShipType];
        }
    }

    public int getShipType(int x, int y, Board board) {
        int shipType;
        if (board.getPosition(x, y).getTeam() == false) {
            shipType = 1;
        } else {
            shipType = 2;
            if (board.getPosition(x, y) instanceof FlagShip) {
                shipType = 3;
            }
        }
        return shipType;
    }
}
