import java.util.ArrayList;
import java.util.Scanner;

public class AI extends Observer {
    private Board board;
    private Game game;
    static Scanner scan = new Scanner(System.in);
    private Boolean team;

    public AI(Game game) {
        this.board = game.getBoard();
        this.game = game;
    }

    public void setTeam(Boolean team) {
        this.team = team;
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
        if (game.getOnTurn()==team){
            ArrayList<Move> path = (ArrayList<Move>) board.gameTree_2(board,team,1,Integer.MIN_VALUE,Integer.MAX_VALUE,0,null).getPath();
            Move move=path.get(0);
            System.out.println(path.toString());
            game.processMove(move.getSrcX(),move.getSrcY(),move.getDstX(),move.getDstY());
            if (move.getWeight()!=2){
                Move secondMove=path.get(1);
                game.processMove(secondMove.getSrcX(),secondMove.getSrcY(),secondMove.getDstX(),secondMove.getDstY());
        }
            board.printBoard(board);
        }
    }}

//todo:  DefensiveStrategy
//    // IF gold: check if flagship is under threat: a silver piece on adjacent diagonal posiion
//    // if silver check if gold can break through to the outer perimeter.

//todo:OffensiveStrategy
//    //silver ships: try to get in pasition to be able to cpature flagship (on diagonal, but with cover
//    //gold: clear way trough silverfleet such that flagship can move through
//todo://some board positions are more interesting than others: middle of board better than close to the edge
//    //captures are more useful than moves unless it's a break trough

