import java.util.Scanner;

public class GUI extends Observer {
    private Board board;
    private Game game;
    static Scanner scan = new Scanner(System.in);

    public GUI(Game game) {
        this.board = game.getBoard();
        this.game = game;
    }

    @Override
    public void update() {
        super.update();
        printBoard(board.getGameBoard());
        Boolean onTurn = game.getOnTurn();
        System.out.println("player on turn" + onTurn);
        requestMove();
    }

    public void printBoard(Ship[][] board) {

        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+");
        int rij, kolom;
        String boatAbreviation = "";
        for (rij = 0; rij < board.length; rij++) {
            System.out.print("|");
            for (kolom = 0; kolom < board[rij].length; kolom++) {
                //if there is no boat: empty position is printed
                if (board[rij][kolom] == null) {
                    boatAbreviation = " ";
                }
                // if there is a golden boat we print g
                else if (board[rij][kolom].getTeam().equals(true)) {
                    boatAbreviation = "g";
                    //if gold and flagship
                    if (board[rij][kolom] instanceof FlagShip) {
                        boatAbreviation = "F";
                    }
                }
                //if silver boat we print s
                else if (board[rij][kolom].getTeam().equals(false)) {
                    boatAbreviation = "s";
                }
                System.out.print(boatAbreviation + "|");
            }
            System.out.println("");
            System.out.println("+-+-+-+-+-+-+-+-+-+-+-+");

        }


    }

    public void requestMove() {
        //geef coördinaten in van positie en gewenste zet
        System.out.println("give x-position of piece you'd like to move ");
        int srcX = scan.nextInt();
        System.out.println("give y-position of piece you'd like to move ");
        int srcY = scan.nextInt();
        System.out.println("give x-position of place you'd like to move your piece to ");
        int dstX = scan.nextInt();
        System.out.println("give y-position of place you'd like to move your piece to ");
        int dstY = scan.nextInt();
        game.processMove(srcX, srcY, dstX, dstY, board.getGameBoard(), board);


    }
}


