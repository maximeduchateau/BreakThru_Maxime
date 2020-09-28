import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    public static final int BOARD_DIM = 11;
    private Ship[][] board;
    private Boolean teamWon;


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
        board[4][4]=new Ship(false);
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
            teamWon = board[srcX][srcY].getTeam();
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
    public int gameTree(Board board, boolean team, int depth, int alpha, int beta) {
        printBoard(board);
        if(depth<=0){
            System.out.println("board evaluation score equals "+ evaluate(board) +" at depth " +depth);
        return evaluate(board);}

        if (board.goldWinningCondition()) {
            return Integer.MAX_VALUE;
        }

        if (board.silverWinningCondition()) {
            return Integer.MIN_VALUE;
        }

        // Find ships of the current player
       Move bestMove;
        int bestResult=team? Integer.MIN_VALUE: Integer.MAX_VALUE;
        System.out.println(bestResult);
        for (int i = 0; i < board.BOARD_DIM; ++i) {
            for (int j = 0; j < board.BOARD_DIM; ++j) {
                System.out.println(i +""+ j);
                Ship srcShip = board.getPosition(i, j);

                // Verify that ship is owned by current player
                if (srcShip != null && srcShip.getTeam() == team) {

                    // Consider all possible moves of the current ship
                    for (Move move : srcShip.generatePossibleMoves(board, i, j)) {

                        Ship dstShip = board.getPosition(move.getDstX(), move.getDstY());

                        // Perform move
                        board.setPosition(move.getDstX(), move.getDstY(), srcShip);
                        board.setPosition(i, j, null);//todo: gaat dit wel werken?

                        // Recursive call
                        int eval =gameTree(board, !team, depth-1, alpha, beta);

                        if (team){alpha = Math.max(alpha, eval);
                            System.out.println("team "+ team+" alpha "+alpha+ " eval "+eval+ "beta "+beta);}
                        else{beta=Math.min(beta,eval);}
                        if (beta<=alpha){
                            System.out.println("BETA <=ALPHA ");
                            //if beta<=alpha we want to break but we first undo move!
                            board.setPosition(i, j, srcShip);
                            board.setPosition(move.getDstX(), move.getDstY(), dstShip);
                            break;
                        }
                        bestResult= team? Math.max(eval,bestResult): Math.min(eval,bestResult);
                        if (eval==bestResult)
                        {bestMove=move;}
                        // Undo move
                        board.setPosition(i, j, srcShip);
                        board.setPosition(move.getDstX(), move.getDstY(), dstShip);
                    }
                }
            }
        }

        return bestResult;
    }
    public void printBoard(Board board) {

        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+");
        int row, column;
        for (row = 0; row < Board.BOARD_DIM; row++) {
            if (row<2) {System.out.print((11-row) +"|");}
            else {
                System.out.print(" "+ (11-row) +"|");}
            for (column = 0; column < Board.BOARD_DIM; column++) {
                //if there is no boat: empty position is printed
                if (board.getPosition(row, column) == null) {
                    System.out.print(" |");
                } else {
                    System.out.print(board.getPosition(row, column).toString() + "|");
                }
            }
            System.out.println("");
            System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+");}
        System.out.println("  +a+b+c+d+e+f+g+h+i+j+k+");

    }
    static int evaluate(Board board){
        int boardRating=0;
        for (int i=0;i<board.BOARD_DIM;++i){
            for (int j=0; j<board.BOARD_DIM;++j){
                Ship ship = board.getPosition(i,j);
                if (ship==null){continue;
                }
                if(ship.getTeam()){
                    boardRating+=ship.value();
                }
                else {boardRating-=ship.value();
                }
            }}
        return boardRating;}

    public List<Object> gameTree_2(Board board, boolean team, int depth, int alpha, int beta) {
        printBoard(board);
        if(depth<=0){
            System.out.println("board evaluation score equals "+ evaluate(board) +" at depth " +depth);
            ArrayList<Move> path = new ArrayList();
            List<Object> list = new ArrayList<Object>();
            list.add(evaluate(board));
            list.add(path);
            return (list);}

        if (board.goldWinningCondition()) {
            ArrayList<Move> path = new ArrayList();
            List<Object> list = new ArrayList<Object>();
            list.add(Integer.MAX_VALUE);
            list.add(path);
            return (list);}


        if (board.silverWinningCondition()) {
            ArrayList<Move> path = new ArrayList();
            List<Object> list = new ArrayList<Object>();
            list.add(Integer.MIN_VALUE);
            list.add(path);
            return (list);}


        // Find ships of the current player
        ArrayList<Move>path=new ArrayList<>();
        Move bestMove;
        int bestResult=team? Integer.MIN_VALUE: Integer.MAX_VALUE;
        System.out.println(bestResult);

            for (int i = 0; i < board.BOARD_DIM; ++i) {
                for (int j = 0; j < board.BOARD_DIM; ++j) {
                    Ship srcShip = board.getPosition(i, j);

                    // Verify that ship is owned by current player
                    if (srcShip != null && srcShip.getTeam() == team) {

                        // Consider all possible moves of the current ship
                        for (Move move : srcShip.generatePossibleMoves(board, i, j)) {

                            Ship dstShip = board.getPosition(move.getDstX(), move.getDstY());

                            // Perform move
                            board.setPosition(move.getDstX(), move.getDstY(), srcShip);
                            board.setPosition(i, j, null);
                            // Recursive call
                            List<Object> gameTree = gameTree_2(board, !team, depth - 1, alpha, beta);
                            Integer eval = (int) gameTree.get(0);
                            if (team) {
                                alpha = Math.max(alpha, eval);
                                System.out.println("team " + team + " alpha " + alpha + " eval " + eval + "beta " + beta);
                            } else {
                                beta = Math.min(beta, eval);
                            }

                            Integer bestResultPrevious = bestResult;
                            bestResult = team ? Math.max(eval, bestResult) : Math.min(eval, bestResult);
                            if (bestResultPrevious != bestResult) {
                                bestMove = move;
                                System.out.println(move.toString());
                                path.add(bestMove);
                                path.addAll((ArrayList<Move>) gameTree.get(1));
                                System.out.println(path);
                            }

                            // Undo move
                            board.setPosition(i, j, srcShip);
                            board.setPosition(move.getDstX(), move.getDstY(), dstShip);
                            if (alpha>=beta){break;}

                        }

                    }
                }
            }

        List<Object> list = new ArrayList<Object>();
        list.add(bestResult);
        list.add(path);
        return (list);}
    }