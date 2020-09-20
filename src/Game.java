public class Game extends Subject {
    private Board board;
    private int numberOfMovesForTurn;
    private Boolean onTurn;

    public Game() {
        this.board = new Board();
    }

    public void start() {
        board.initialize();
        //Gold starts (when on Turn is true, it's golds turn)
        onTurn = true;
        notify();
        if (numberOfMovesForTurn == -1) {
            board.setTeamWon(!onTurn);
        }

    }

    public Boolean getOnTurn() {
        return onTurn;
    }

    public Board getBoard() {
        return board;
    }

    public void processMove(int srcX, int srcY, int dstX, int dstY, Ship[][] board, Board Board) {
        //invalid if a ship is moved, if it isn't that teams turn
        Boolean nonEmptyStartPosition = (board[srcX][srcY] != null);
        Boolean nonEmptyTargetPosition = (board[dstX][dstY] != null);
        int moveEvaluation = 0;
        //if empty startposition-->invalid move
        if (!nonEmptyStartPosition) {
            moveEvaluation = -1;
        } else {
            if (nonEmptyTargetPosition) {
                moveEvaluation = board[srcX][srcY].isValidCapture(srcX, srcY, dstX, dstY, board);
            } else {
                moveEvaluation = board[srcX][srcY].isValidMove(srcX, srcY, dstX, dstY, board);
            }
        }
        if (moveEvaluation == -1) {
            System.out.println("invalid move");
            numberOfMovesForTurn = -1;
        } else {
            numberOfMovesForTurn += moveEvaluation;
            Board.move(srcX, srcY, dstX, dstY);
            if (numberOfMovesForTurn == 2) {
                onTurn = !onTurn;
                numberOfMovesForTurn = 0;
            }

            if (numberOfMovesForTurn < 2) {
                notify();
            }
            if (numberOfMovesForTurn > 2) {
                numberOfMovesForTurn = -1;
            }
        }
    }
}
