import java.util.Scanner;
public class Main {
static Scanner scan=new Scanner(System.in);
    public static void main(String[] args) {
        Game game = new Game();
        GUI gui = new GUI(game);
        AI ai = new AI(game);
        game.attach(gui);
        game.attach(ai);


        //if we play with coinflip
        //coinflip(gui, ai);

        //if AI is golden team
        System.out.println("ai plays gold (true), you play silver (false)");
        gui.setTeam(false);
        ai.setTeam(true);
game.start(true);}
//        if AI is silver team
//
//        System.out.println("ai plays silver (false), you play gold (true)");
//        gui.setTeam(true);
//        ai.setTeam(false);
//        System.out.println("Golden player, do you want to pass the first move: then write yes !");
//        if (scan.nextLine().equals("yes")){
//        game.start(false);}
//        else {game.start(true);}}

//    static void coinflip(GUI gui, AI ai){
//        double coinFlip=Math.random();
//        if (coinFlip>0.5){gui.setTeam(false); ai.setTeam(true);
//            System.out.println("ai plays gold (true), you play silver (false)");}
//        else{gui.setTeam(true); ai.setTeam(false);
//            System.out.println("ai plays silver (false), you play gold (true)");}
//
//    }
}
