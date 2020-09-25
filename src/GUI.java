import java.util.Scanner;

public class GUI extends Observer {
    private Board board;
    private Game game;
    static Scanner scan = new Scanner(System.in);
    private Boolean team;

    public GUI(Game game) {
        this.board = game.getBoard();
        this.game = game;
    }
    public void setTeam(Boolean team){
        this.team= team;
    }
    public Boolean getTeam(){
        return team;
    }
    @Override
    public void update() {
        super.update();
        printBoard(board);
        Boolean onTurn = game.getOnTurn();
        System.out.println("you are the " + getTeam()+ " player");
        System.out.println("player on turn" + onTurn);
        requestMove();
    }

    public void printBoard(Board board) {

        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+");
        int row, column;
        for (row = 0; row < Board.BOARD_DIM; row++) {
            if (row<9) {System.out.print(" " + (row+1) +"|");}
            else {
                System.out.print(row+1 +"|");}
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

    public void requestMove() {
        //geef coördinaten in van positie en gewenste zet
        System.out.println("give boardcoordinate of ship you'd like to move");
        String srcCoordinates=scan.nextLine();
        int srcX = xCoordinate(srcCoordinates);
        int srcY = yCoordinate(srcCoordinates);
        System.out.println("give boardcoordinate of position you'd like to move to");
        String dstCoordinates=scan.nextLine();
        int dstX = xCoordinate(dstCoordinates);
        int dstY = yCoordinate(dstCoordinates);
        game.processMove(srcX, srcY, dstX, dstY);


    }
    private Integer yCoordinate(String input){
       Integer y = null;
        String alphabet="abcdefghijk";
        Character yLetter=input.charAt(0);
        for (int i=0; i<alphabet.length();++i){
            if (alphabet.charAt(i)==yLetter){
                y=i;
                break;
            }
        }
        return y;
    }
    private Integer xCoordinate(String input){
        Integer x = board.BOARD_DIM- strToNumber(input.substring(1),0);
        return x;
        }

    static int strToNumber(String a, int index) {
        if (index < a.length() - 1) {
            return (int) ((a.charAt(index) - 48) * (Math.pow(10, a.length()-1-index))+strToNumber(a, index + 1) );
        } else {
            return (int) ((a.charAt(index) - 48) * (Math.pow(10, a.length()-1-index)));
        }
    }
}






