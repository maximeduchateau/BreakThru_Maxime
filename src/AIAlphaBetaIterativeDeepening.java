import java.util.Scanner;

public class AIAlphaBetaIterativeDeepening extends Observer {
    private Board board;
    private Game game;
    static Scanner scan = new Scanner(System.in);
    private Boolean team;
    private Move theMove;

    private static final int INITIAL_DEPTH = 5;
    private static final int TIMEOUT_MILLISECONDS = 6000;

    private int currentDepth;
    private Move bestMove;
    private Move globalBestMove;
    private long start;
    private boolean timeout;

    public AIAlphaBetaIterativeDeepening(Game game) {
        this.board = game.getBoard();
        this.game = game;
    }
    public Move decideMove()
    {
        timeout = false;
        start = System.currentTimeMillis();

        for (int d = 0;; d++)
        {
            if (d > 0)
            {
                globalBestMove = bestMove;
                System.out.println("Completed search with depth " + currentDepth + ". Best move so far: " + globalBestMove);
            }
            currentDepth = INITIAL_DEPTH + d;
            gameTree(board, currentDepth, team, Integer.MIN_VALUE, Integer.MAX_VALUE);

            if (timeout)
            {
                System.out.println();
                return globalBestMove;
            }
        }
    }

    static int evaluate(Boolean team, Board board){
        int boardRating=0;
        for (int i=0;i<board.BOARD_DIM;++i){
            for (int j=0; j<board.BOARD_DIM;++i){
             Ship ship = board.getPosition(i,j);
                if (ship==null){continue;
                }
                if(ship.getTeam()==team){
                    boardRating+=ship.value();
                }
                else {boardRating-=ship.value();
            }
        }}
        return boardRating;}


    public void update() {
        super.update();
    }

    public int gameTree(Board board, int depth, boolean team, int alpha, int beta){

        if(board.goldWinningCondition()) {
            return 1000;
        }
        if(board.silverWinningCondition()) {
            return -1000;
        }
        if (System.currentTimeMillis() - start > TIMEOUT_MILLISECONDS)
        {
            timeout = true;
            return alpha;
        }
        if (depth == 0) {
            return evaluate(this.team,board);
        }
        // Find ships of the current player
        int bestResult = -1000;
        int result;
        for(int i = 0; i < board.BOARD_DIM; ++i) {
            for(int j = 0; j < board.BOARD_DIM; ++j) {

                Ship srcShip = board.getPosition(i, j);

                // Verify that ship is owned by current player
                if(srcShip != null && srcShip.getTeam() == team) {

                    // Consider all possible moves of the current ship
                    for(Move move: srcShip.generatePossibleMoves(board,i,j)) {

                        Ship dstShip = board.getPosition(move.getDstX(), move.getDstY());

                        // Perform move
                        board.setPosition(move.getDstX(), move.getDstY(),srcShip);
                        board.setPosition(i, j,null);//todo: gaat dit wel werken?

                        // Recursive call
                        result = -gameTree(board,depth-1, !team, -beta, -alpha);
                        if (result>bestResult){bestResult=result;
                            theMove=move;}
                        // Undo move
                        board.setPosition(i, j,srcShip);
                        board.setPosition(move.getDstX(), move.getDstY(),dstShip);
                        //update alpha and beta
                        if (bestResult>alpha) {
                            alpha=bestResult;
                        if (depth==currentDepth){
                        bestMove=move;}
                        }
                        if (bestResult>=beta){break;}
                    }
                }
            }
        }

        return bestResult;}}
