import java.util.Scanner;

public class AIAlphaBeta extends Observer {
    private Board board;
    private Game game;
    static Scanner scan = new Scanner(System.in);
    private Boolean team;
    private Move theMove;

    public AIAlphaBeta(Game game) {
        this.board = game.getBoard();
        this.game = game;
    }

/*
        private void genNextMove(){
            ArrayList<Move>allPossibleMovesPlayer= new ArrayList<>();
            ArrayList<Move>allPossibleFinalMovesPlayer= new ArrayList<>();

            for (int i=0; i<board.BOARD_DIM;++i){
                for (int j=0; j<board.BOARD_DIM;++j){
                    if(board.getPosition(i,j)!=null && board.getPosition(i,j).getTeam()==team){
                        allPossibleMovesPlayer.addAll(board.getPosition(i,j).generatePossibleMoves(board,i,j));
                    }
                }
            }

 */
            /*
            for (Move m: allPossibleMovesPlayer){
                for (Move n: allPossibleMovesPlayer){
                    if(m.getWeigt()==2){allPossibleFinalMovesPlayer.add(m);
                    continue;}
                    if (m.getDstX()==n.getDstX()&&m.getDstY()==n.getDstY()){
                        continue;
                    }
                    if (m.getSrcX()==n.getSrcX()&&m.getSrcY()==n.getSrcY()){
                        continue;}
                    if (m.getWeigt()+n.getWeigt()== game.MAX_WEIGHT_FOR_TURN){
                        allPossibleFinalMovesPlayer.add();
                    }
                    }
                }

             */


    public void update() {
        super.update();
    }


    public int gameTree(Board board, boolean team, int alpha, int beta) {

        if (board.goldWinningCondition()) {
            return 10000;
        }

        if (board.silverWinningCondition()) {
            return -10000;
        }

        // Find ships of the current player
        int bestResult = -1000;
        int result;
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
                        board.setPosition(i, j, null);//todo: gaat dit wel werken?

                        // Recursive call
                        result = -gameTree(board, !team, -beta, -alpha);
                        if (result > bestResult) {
                            bestResult = result;
                            theMove = move;
                        }
                        // Undo move
                        board.setPosition(i, j, srcShip);
                        board.setPosition(move.getDstX(), move.getDstY(), dstShip);
                        //update alpha and beta
                        if (bestResult > alpha) {
                            alpha = bestResult;
                        }
                        if (bestResult >= beta) {
                            break;
                        }
                    }
                }
            }
        }

        return bestResult;
    }
}

