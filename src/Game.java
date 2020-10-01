public class Game extends Subject {

    public final static int MAX_WEIGHT_FOR_TURN = 2;

    private Board board;
    private int numberOfMovesForTurn;
    private Boolean onTurn;         //Gold starts (when on Turn is true, it's golds turn)

    public Game() {
        this.board = new Board();
    }

    public void start() {
        board.initialize();
        onTurn = true;
        notifyObservers(UpdateType.PLAYER_UPDATE);
    }

    public Boolean getOnTurn() {
        return onTurn;
    }

    public Board getBoard() {
        return this.board;
    }

    public void processMove(int srcX, int srcY, int dstX, int dstY) {

        if (isValidMove(srcX, srcY, dstX, dstY)) {
            board.move(srcX, srcY, dstX, dstY);
            if (board.getTeamWon() != null) {
                System.out.println("GAME OVER, winner is" + board.getTeamWon().toString());
            }
        }
        switchSidesIfNecessary();
        notifyObservers(UpdateType.BOARD_UPDATE);
    }

    private Boolean isValidMove(int srcX, int srcY, int dstX, int dstY) {
        Boolean validMove = true;
        int moveEvaluation;
        if (srcX < 0 || srcX > board.BOARD_DIM - 1 || srcY < 0 || srcY > board.BOARD_DIM - 1 || dstX < 0 || dstX > board.BOARD_DIM - 1 || dstY < 0 || dstY > board.BOARD_DIM - 1) {
            return false;
        }
        if (board.getPosition(srcX, srcY) == null ||
                board.getPosition(srcX, srcY).getTeam() != onTurn ||
                srcX == dstX && srcY == dstY) {
            validMove = false;
        }
        if (validMove) {
            // (board.getPosition(dstX, dstY) != null && numberOfMovesForTurn == 0)
            if (board.getPosition(dstX, dstY) != null) {
                moveEvaluation = board.getPosition(srcX, srcY).isValidCapture(srcX, srcY, dstX, dstY, board);
            } else {
                moveEvaluation = board.getPosition(srcX, srcY).isValidMove(srcX, srcY, dstX, dstY, board);
            }
            numberOfMovesForTurn += moveEvaluation;
            if (moveEvaluation == -1) {
                validMove = false;
            }
        }

        return validMove;
    }

    private void switchSidesIfNecessary() {
        if (numberOfMovesForTurn == MAX_WEIGHT_FOR_TURN) {
            onTurn = !onTurn;
            numberOfMovesForTurn = 0;
            this.notifyObservers(UpdateType.PLAYER_UPDATE);
        }
    }

}

