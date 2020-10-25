import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//Specify the membervariables

public class AI extends Observer {
    private Board board;
    private Game game;
    static Scanner scan = new Scanner(System.in);
    private Boolean team;
    private int depth = 2;
    private long[][] zobrist = new long[121][3];
    private HashMap<Boolean, HashMap<Long, TTEntry>> transpositionTable;

//constructor

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

// The updatefunction --> prints the board
//                    --> calls the alphabeta search for the current board
//                    --> performs the move/moves
//                    --> prints the path and possibly the win of one of the teams

    public void update(UpdateType updateType) {
        if (updateType == UpdateType.PLAYER_UPDATE) {
            board.printBoard(board);
            if (game.getOnTurn() == team) {
//

                ArrayList<PairTuple> path = (ArrayList<PairTuple>) alphabetasearch(board, team, this.depth, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, null, null).getPath();

                int i = 0;
                while (i < path.size()) {

                    PairTuple move = path.get(i);
                    if (move.getDepth() != this.depth) {
                        break;
                    }
                    System.out.println("move from "+lettercoordinate(move.getMove().getSrcY())+numbercoordinate(move.getMove().getSrcX())+" to "+lettercoordinate(move.getMove().getDstY())+numbercoordinate(move.getMove().getDstX()));
                    game.processMove(move.getMove().getSrcX(), move.getMove().getSrcY(), move.getMove().getDstX(), move.getMove().getDstY());
                    ++i;

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
//use this to print the move made by the AI
    private char lettercoordinate(int y){
        String alphabet="abcdefghijk";
        return alphabet.charAt(y);
        }

//use this to print the move made by the AI
    private Integer numbercoordinate(int x){
        Integer number = board.BOARD_DIM-x ;
        return number;
    }


    public Pair alphabetasearch(Board board, boolean team, int depth, int alpha, int beta, int currentWeight, Move lastMove, Long key) {
        if (key == null) {
            key = computeKey(board);
        }
        //switch teams and increase depth by one if the maxweight (2) is reached!

        if (currentWeight == Board.MAX_WEIGHT_PER_TURN) {
            team = !team;
            depth -= 1;
            currentWeight = 0;
            lastMove = null;
        }

        //if the position already can be found in the TT table
        // and the current bounds lie within the bounds in the TT
        // and the depth in the TT is at least the search depth
        // --> use the TT value and move

        if (currentWeight == 0) {
            if (transpositionTable.get(team).containsKey(key)) {
                TTEntry entry = transpositionTable.get(team).get(key);
                if (entry.getDepth() >= depth && entry.getAlpha() <= alpha+1 && entry.getBeta() >= beta-1) {
                    return new Pair(entry.getValue(), entry.getPath());
                }

            }
        }
        //these are te base conditions: depth zero or a winning condition: in this case we evaluate the board!
        if (depth <= 0 || board.silverWinningCondition() || board.goldWinningCondition()) {
            return new Pair(board.evaluate(board,team), new ArrayList<>());
        }



        ArrayList<Move> path = new ArrayList<>();
        Pair optimal = new Pair(team ? Integer.MIN_VALUE : Integer.MAX_VALUE, null);
        PairTuple bestMove = null;

        //iterate over the entire board to find ships of the team at play and generate all the possible moves

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

                        //if another move has already been made you cannot move the flagship or make a capture as second move!

                        if (move.getWeight() + currentWeight > board.MAX_WEIGHT_PER_TURN) {
                            continue;
                        }
                        //update the Zobristkey

                        long updatedKey = updateKey(key, move, board);
                        Ship dstShip = board.getPosition(move.getDstX(), move.getDstY());

                        //Perform move

                        board.setPosition(move.getDstX(), move.getDstY(), srcShip);
                        board.setPosition(i, j, null);

                        // Recursive call

                        Pair gameTree = alphabetasearch(board, team, depth, alpha, beta, currentWeight + move.getWeight(), move, updatedKey);

                        //make a new entry in the TT

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
        //System.out.println("evaluationvalue of best "+optimal.getValue());
        return result;
    }

//generate random numbers
    public static long random64() {
        SecureRandom random = new SecureRandom();
        return random.nextLong();
    }


//this function is called in the constructor to make all the keys
    public void fillArray() {
        for (int boardPos = 0; boardPos < 121; boardPos++) {
            for (int pieceType = 0; pieceType < 3; pieceType++) {

                zobrist[boardPos][pieceType] = random64();
            }
        }
    }
// computes the zobrist key
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
//incrementally updat the zobrist key
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
//function to get the ship type needed to compute the key
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

