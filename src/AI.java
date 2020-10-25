import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class AI extends Observer {
    private Board board;
    private Game game;
    static Scanner scan = new Scanner(System.in);
    private Boolean team;
    private int depth = 2;
    private long[][] zobrist = new long[121][3];
    private HashMap<Boolean, HashMap<Long, TTEntry>> transpositionTable;



//    private static final int TIMEOUT_MILISECONDS = 100000;
//
//    private long start;
//    private boolean timeout;


    public AI(Game game) {
        this.board = game.getBoard();
        this.game = game;
        fillArray();
        transpositionTable = new HashMap<>();
        transpositionTable.put(true, new HashMap<>());
        transpositionTable.put(false, new HashMap<>());

    }

    public void setTeam(Boolean team) {
        this.team = team;
    }


    public void update(UpdateType updateType) {
        if (updateType == UpdateType.PLAYER_UPDATE) {
            board.printBoard(board);
            if (game.getOnTurn() == team) {
//                timeout=false;
//                start = System.currentTimeMillis();
//                ArrayList<PairTuple> path = null;
//                for (int extraDepth=0;!timeout;extraDepth++ ) {
                //ArrayList<PairTuple> path = (ArrayList<PairTuple>) alphabetasearch(board, team, this.depth, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, null, null).getPath();
                ArrayList<PairTuple> path = (ArrayList<PairTuple>) alphabetasearch(board, team, this.depth, -1000, 1000, 0, null, null).getPath();
//                    if (System.currentTimeMillis()-start>TIMEOUT_MILISECONDS){
//                        timeout=true;
//                        System.out.println(extraDepth);
//                    }
//                }
                int i = 0;
                while (i < path.size()) {

                    PairTuple move = path.get(i);
                    if (move.getDepth() != this.depth) {
                        break;
                    }
                    System.out.println(path.toString());
                    game.processMove(move.getMove().getSrcX(), move.getMove().getSrcY(), move.getMove().getDstX(), move.getMove().getDstY());
                    ++i;

                    //TODO: nu even hier game over geprint maar beter moet dit cleaner worden gedaan later
                    if (board.goldWinningCondition()) {
                        System.out.println("GAME OVER GOLD WON");
                    }
                    if (board.silverWinningCondition()) {
                        System.out.println("GAME OVER SILVER WON");
                    }
                }

            }
        }
    }

    public Pair alphabetasearch(Board board, boolean team, int depth, int alpha, int beta, int currentWeight, Move lastMove, Long key) {
        if (key == null) {
            key = computeKey(board);
        }
        if (currentWeight == Board.MAX_WEIGHT_PER_TURN) {
            team = !team;
            depth -= 1;
            currentWeight = 0;
            lastMove = null;
        }
        if (currentWeight == 0) {
            if (transpositionTable.get(team).containsKey(key)) {
                TTEntry entry = transpositionTable.get(team).get(key);
                //todo: check depth
                if (entry.getDepth() >= depth && entry.getAlpha() <= alpha+1 && entry.getBeta() >= beta-1) {
                   // System.out.println("tt used with: beta "+beta +"alpha "+alpha+ "beta of TT "+ entry.getBeta()+ "alpha of TT" +entry.getAlpha());
                    return new Pair(entry.getValue(), entry.getPath());
                }
//                if (entry.getDepth() >= depth){
//                    System.out.println("tt NOT USED BOUNDS: beta :"+beta +"alpha: "+alpha+ "beta of TT :"+ entry.getBeta()+ "alpha of TT : " +entry.getAlpha());
//                }
            }
        }
        if (depth <= 0 || board.silverWinningCondition() || board.goldWinningCondition()) {
            return new Pair(board.evaluate(board,team), new ArrayList<>());
        }


        // Find ships of the current player
        ArrayList<Move> path = new ArrayList<>();
        Pair optimal = new Pair(team ? -1000 : 1000, null);
        PairTuple bestMove = null;

        for (int i = 0; i < board.BOARD_DIM && alpha < beta; ++i) {
            for (int j = 0; j < board.BOARD_DIM && alpha < beta; ++j) {
                Ship srcShip = board.getPosition(i, j);

                //skip if current ship was moved in previous iteration
                if (lastMove != null && i == lastMove.getDstX() && j == lastMove.getDstY()) {
                    continue;
                }

                // Verify that ship is owned by current player
                if (srcShip != null && srcShip.getTeam() == team) {

                    // Consider all possible moves of the current ship
                    for (Move move : srcShip.generatePossibleMoves(board, i, j, board.MAX_WEIGHT_PER_TURN - currentWeight)) {
                        if (move.getWeight() + currentWeight > board.MAX_WEIGHT_PER_TURN) {
                            continue;
                        }
                        Ship dstShip = board.getPosition(move.getDstX(), move.getDstY());
                        long updatedKey = updateKey(key, move, board);
                        //Perform move
                        board.setPosition(move.getDstX(), move.getDstY(), srcShip);
                        board.setPosition(i, j, null);

                        // Recursive call
                        Pair gameTree = alphabetasearch(board, team, depth, alpha, beta, currentWeight + move.getWeight(), move, updatedKey);
                        TTEntry ttEntry = new TTEntry(gameTree.getValue(), alpha, beta, depth, gameTree.getPath());
                        transpositionTable.get(team).put(key, ttEntry);

                        if (team && gameTree.getValue() >= optimal.getValue() || !team && gameTree.getValue() <= optimal.getValue()) {
                            bestMove = new PairTuple(move, depth);
                            optimal = gameTree;
                        }
                        // Undo move
                        board.setPosition(i, j, srcShip);
                        board.setPosition(move.getDstX(), move.getDstY(), dstShip);

                        if (team) {
                            alpha = Math.max(alpha, gameTree.getValue());
                            //System.out.println("team " + team + " alpha " + alpha + " eval " + gameTree.getValue() + "beta " + beta);
                        } else {
                            beta = Math.min(beta, gameTree.getValue());
                        }

                        if (beta <= alpha) {
                            break;
                        }
                    }

                }
            }
        }
        Pair result = new Pair(optimal.getValue(), new ArrayList<>());
        result.getPath().addAll(optimal.getPath());
        result.getPath().add(0, bestMove);
        System.out.println("evaluationvalue of best "+optimal.getValue());
        return result;
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
        int srcPos = move.getSrcX() * 11 + move.getSrcY();
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
        if (board.getPosition(x, y) != null && board.getPosition(x, y).getTeam() == false) {
            shipType = 0;
        } else {
            shipType = 1;
            if (board.getPosition(x, y) instanceof FlagShip) {
                shipType = 2;
            }
        }
        return shipType;
    }
}

