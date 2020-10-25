import java.util.Scanner;
public class Main {
static Scanner scan=new Scanner(System.in);
    public static void main(String[] args) {
        Game game = new Game();
        GUI gui = new GUI(game);
        AI ai = new AI(game);
        game.attach(gui);
        game.attach(ai);


//      //A: if we play with coinflip

        //coinflip(gui, ai);

        //B: if AI is golden team
//
//        System.out.println("ai plays gold, you play silver");
//        gui.setTeam(false);
//        ai.setTeam(true);
//game.start(true);}


        //C: if AI is silver team

        System.out.println("ai plays silver, you play gold");
        gui.setTeam(true);
        ai.setTeam(false);
        System.out.println("Golden player, do you want to pass the first move: then write yes !");
        if (scan.nextLine().equals("yes")){
        game.start(false);}
        else {game.start(true);}}


        // coin flip randomly decides which player plays gold or silver.

//    static void coinflip(GUI gui, AI ai){
//        double coinFlip=Math.random();
//        if (coinFlip>0.5){gui.setTeam(false); ai.setTeam(true);
//            System.out.println("ai plays gold, you play silver");}
//        else{gui.setTeam(true); ai.setTeam(false);
//            System.out.println("ai plays silver, you play gold");}
//
//    }
}
